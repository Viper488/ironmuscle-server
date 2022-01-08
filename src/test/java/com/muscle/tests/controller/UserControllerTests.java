package com.muscle.tests.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.muscle.user.UserController;
import com.muscle.user.dto.IronUserDetails;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.response.UserResponse;
import com.muscle.user.service.BadgeService;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    private final String SECRET_KEY = "DB@YG831GHT@";
    private final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET_KEY.getBytes());

    @Autowired
    private MockMvc mvc;

    @MockBean
    UserService userService;
    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    BadgeService badgeService;


    @Test
    public void returnGetMyself() throws Exception {
        Role role = new Role(1L, "USER");
        IronUser user = IronUser.builder()
                .id(1L)
                .username("alex")
                .email("alex@gmail.com")
                .password("Alex#123")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(role))
                .build();
        IronUserDetails userDetails = new IronUserDetails(user.dto());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/login");

        when(jwtUtil.createToken(any(MockHttpServletRequest.class), any(UserDetails.class), any(Integer.class))).thenReturn(JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(ALGORITHM));

        String token = jwtUtil.createToken(request, userDetails, 1000 * 60);

        UserResponse response = UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        when(userService.getMyself(any(String.class))).thenReturn(response);

        mvc.perform(get("/api/v1/myself")
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.email", is(user.getEmail())));
    }

    @Test
    public void getPaginatedUsers_withoutFiltering() throws Exception {
        Role role = new Role(1L, "USER");
        IronUser user = IronUser.builder()
                .id(1L)
                .username("alex")
                .email("alex@gmail.com")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(role))
                .build();

        Page<IronUser> userPage = new PageImpl<>(Collections.singletonList(user));
        Pageable pageable = PageRequest.of(0, 100);

        when(userService.getPaginatedUsers(pageable, "")).thenReturn(userPage);

        mvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "100")
                .param("query", ""))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.users[0].username", is(user.getUsername())));
    }
}
