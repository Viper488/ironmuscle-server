package com.muscle.user;

import com.muscle.user.dto.IronUserDetails;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

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
    public void createJWToken_changeUserImage() throws Exception {

        Role role = new Role(1L, "USER");
        IronUser user = new IronUser(1L, "username", "email", "password", "profile-picture/default/icon.png", false, true, Collections.singletonList(role));
        IronUserDetails userDetails = new IronUserDetails(user.dto());

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/api/v1/login");

        when(jwtUtil.createToken(request, userDetails, 1000 * 60)).thenReturn("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1Ni" +
                        "J9.eyJzdWIiOiJ1c2VybmFtZSIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9hcGkvdjEvbG9naW4iLCJleHAiOj" +
                "E2NDE1NTg3NjMsImlhdCI6MTY0MTQ3MjM2MywiYXV0aG9yaXRpZXMiOlsiVVNFUiJdfQ.JBjiZGjZMjNyx13SX25-GEUxhx8c-235" +
                "WjTQC4hJtIs");

        String access_token = jwtUtil.createToken(request, userDetails, 1000 * 60);

        byte[] image = Files.readAllBytes(Paths.get("src/main/resources/images/" + "profile-picture/default/icon.png"));
        MultipartFile file = new MockMultipartFile("icon.png", "icon.png", "image/png", image);

        doNothing().when(userService).changeUserIcon("Bearer " + access_token, file);

        mvc.perform(put("/api/v1/user/icon")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Authorization", "Bearer " + access_token)
                .content(file.getBytes()))
                .andExpect(status().isOk());
    }

    @Test
    public void getPaginatedUsers_withoutFiltering() throws Exception {
        Role role = new Role(1L, "USER");
        IronUser user = new IronUser(1L, "username", "email", "password", "profile-picture/default/icon.png", false, true, Collections.singletonList(role));

        Page<IronUser> userPage = new PageImpl<>(Collections.singletonList(user));
        Pageable pageable = PageRequest.of(0, 100);

        when(userService.getPaginatedUsers(pageable, "")).thenReturn(userPage);

        mvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "100")
                .param("query", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.users[0].username", is(user.getUsername())));
    }
}
