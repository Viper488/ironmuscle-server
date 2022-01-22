package com.muscle.tests.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.muscle.user.RegistrationController;
import com.muscle.user.dto.RegistrationRequestDto;
import com.muscle.user.service.RegistrationService;
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
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(RegistrationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerTests {

    @MockBean
    UserService userService;
    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    RegistrationService registrationService;
    RegistrationRequestDto validRequest;
    RegistrationRequestDto wrongRequest;
    @Autowired
    private MockMvc mvc;

    public static String asJsonString(Object anyObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(anyObject);
    }

    @Before
    public void setupValidRequestAndWrongRequest() {
        validRequest = new RegistrationRequestDto(
                "alex",
                "alex@mail.com",
                "Alex#123",
                Collections.singletonList("USER"));
        wrongRequest = new RegistrationRequestDto(
                "alex",
                "alexmail.com",
                "Alex#123",
                Collections.singletonList("USER"));
    }

    @Test
    public void registerUser_Successfully() throws Exception {
        doNothing().when(registrationService).register(validRequest);

        mvc.perform(post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void registerUser_Unsuccessfully_BadEmail() throws Exception {
        doThrow(new IllegalStateException("Email is not valid")).when(registrationService).register(wrongRequest);

        mvc.perform(post("/api/v1/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(wrongRequest)))
                .andExpect(status().is4xxClientError());
    }
}

