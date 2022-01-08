package com.muscle.tests.service;

import com.IronMuscleApplication;
import com.muscle.user.dto.IronUserDetails;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    public void whenUploadedIconSaved_thenCheckIfExists_thenCheckIfEqualsPathInUser() throws IOException {
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

        MockMultipartFile file = new MockMultipartFile("file", "filename.png", "image/png", "example".getBytes());

        userService.saveImage(user, file.getBytes());

        String MAIN_DIR = "src/main/resources/images/";
        String SUB_DIR = "/profile-picture/" + user.getId().toString() + "/";
        String FILE_LOCATION = SUB_DIR + user.getId().toString() + ".png";

        Path path = Paths.get(MAIN_DIR + FILE_LOCATION);
        assertTrue(Files.exists(path));

        IronUser found = userService.getUserByUsername(user.getUsername());

        assertEquals(FILE_LOCATION, found.getIcon());
    }
}
