package com.muscle.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.muscle.trainings.TrainingsController;
import com.muscle.trainings.entity.Training;
import com.muscle.trainings.other.CreateTrainingDto;
import com.muscle.trainings.service.TrainingExerciseService;
import com.muscle.trainings.service.TrainingsService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@Slf4j
@RunWith(SpringRunner.class)
@WebMvcTest(TrainingsController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TrainingsControllerTests {

    @Autowired
    private MockMvc mvc;

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

    @Test
    public void saveTraining_Successfully() throws Exception {
        CreateTrainingDto createTrainingDto = new CreateTrainingDto("name", "standard", "abdominal", "beginner", 10);
        Training training = new Training(1L, "name", "standard", "training/abdominal_beginner.png", "beginner", 10);

        when(trainingsService.saveTraining(createTrainingDto)).thenReturn(training.response());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(createTrainingDto);

        mvc.perform(post("/api/v1/training")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(training.getName())))
                .andExpect(jsonPath("$.type", is(training.getType())))
                .andExpect(jsonPath("$.difficulty", is(training.getDifficulty())));
    }

    @Test
    public void getPaginatedTrainings_withoutFiltering() throws Exception {
        Training training = new Training(1L, "name", "standard", "training/abdominal_beginner.png", "beginner", 10);

        List<Training> trainings = new ArrayList<>();
        trainings.add(training);

        Page<Training> trainingPage = new PageImpl<>(trainings);
        Pageable pageable = PageRequest.of(0, 100);

        when(trainingsService.getPaginatedTrainings(pageable, "")).thenReturn(trainingPage);

        mvc.perform(get("/api/v1/training/all")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "100")
                .param("query", ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPage", is(0)))
                .andExpect(jsonPath("$.totalPages", is(1)))
                .andExpect(jsonPath("$.trainings[0].name", is(training.getName())));
    }
}