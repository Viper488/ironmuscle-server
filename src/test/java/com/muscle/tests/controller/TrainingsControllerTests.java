package com.muscle.tests.controller;

import com.muscle.trainings.TrainingsController;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.other.CreateTrainingDto;
import com.muscle.trainings.service.TrainingExerciseService;
import com.muscle.trainings.service.TrainingsService;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.BeforeClass;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static com.muscle.tests.controller.RegistrationControllerTests.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(TrainingsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainingsControllerTests {

    @MockBean
    UserService userService;
    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @MockBean
    JwtUtil jwtUtil;
    @MockBean
    TrainingsService trainingsService;
    @MockBean
    TrainingExerciseService trainingExerciseService;
    CreateTrainingDto createTrainingDto;
    Training training;
    @Autowired
    private MockMvc mvc;

    @Before
    public void setupCreateTrainingAndTraining() {
        createTrainingDto = new CreateTrainingDto(
                "name",
                "standard",
                "abdominal",
                "beginner",
                10);
        training = new Training(
                1L,
                "name",
                "standard",
                "training/abdominal_beginner.png",
                "beginner", 10);
    }

    @Test
    public void saveTraining_Successfully() throws Exception {
        when(trainingsService.saveTraining(createTrainingDto)).thenReturn(training.response());

        mvc.perform(post("/api/v1/training")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(createTrainingDto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.name", is(training.getName())))
                .andExpect(jsonPath("$.type", is(training.getType())))
                .andExpect(jsonPath("$.difficulty", is(training.getDifficulty())));
    }

    @Test
    public void getPaginatedTrainings_withoutFiltering() throws Exception {
        List<Training> trainings = Collections.singletonList(training);

        Page<Training> trainingPage = new PageImpl<>(trainings);
        Pageable pageable = PageRequest.of(0, 100);

        when(trainingsService.getPaginatedTrainings(pageable, "")).thenReturn(trainingPage);

        mvc.perform(get("/api/v1/training/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "100")
                .param("query", ""))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.trainings[0].name", is(training.getName())));
    }
}
