package com.muscle.tests.controller;

import com.muscle.user.UserController;
import com.muscle.user.dto.IronUserDetails;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.response.UserResponse;
import com.muscle.user.service.BadgeService;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {
    @MockBean
    UserService userService;
    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    JwtUtil jwtUtilBean;
    @MockBean
    BadgeService badgeService;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtUtil jwtUtil;

    private IronUser user;

    @Before
    public void setupUser() {
        user = IronUser.builder()
                .id(1L)
                .username("alex")
                .email("alex@gmail.com")
                .password("Alex#123")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(new Role(1L, "USER")))
                .build();
    }

    @Test
    public void returnAuthorizedUserInformation() throws Exception {
        IronUserDetails userDetails = new IronUserDetails(user.dto());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/login");

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
                .andExpect(jsonPath("$.totalItems", is(1)))
                .andExpect(jsonPath("$.users[0].username", is(user.getUsername())));
    }
}
