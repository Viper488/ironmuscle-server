package com.muscle.tests.repository;

import com.muscle.trainings.entity.TrainingRequest;
import com.muscle.trainings.repository.TrainingRequestRepository;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.Role;
import com.muscle.user.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TrainingRequestRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TrainingRequestRepository trainingRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setupUserAndTrainer() {
        IronUser user = IronUser.builder()
                .username("alex")
                .email("alex@gmail.com")
                .password("Alex#123")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(new Role(1L, "USER")))
                .build();

        IronUser trainer = IronUser.builder()
                .username("james")
                .email("james@gmail.com")
                .password("James#123")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(new Role(2L, "TRAINER")))
                .build();

        entityManager.persist(user);
        entityManager.persist(trainer);
        entityManager.flush();
    }

    @Test
    public void createTrainingRequest_thenReturnPage() {
        Optional<IronUser> user = userRepository.findByUsername("alex");
        Optional<IronUser> trainer = userRepository.findByUsername("james");

        assertTrue(user.isPresent());
        assertTrue(trainer.isPresent());

        TrainingRequest trainingRequest = TrainingRequest.builder()
                .title("request")
                .description("description")
                .difficulty("beginner")
                .status("new")
                .bodyPart("arms")
                .created_at(LocalDateTime.now())
                .user(user.get())
                .build();

        entityManager.persist(trainingRequest);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 100);
        Page<TrainingRequest> trainingRequestPage = trainingRequestRepository.findByUserIdAndStatusAndQuery(user.get().getId(), "new", "request", pageable);

        assertEquals(0, trainingRequestPage.getNumber());
        assertEquals(1, trainingRequestPage.getTotalElements());
        assertEquals(1, trainingRequestPage.getContent().size());
        assertEquals("request", trainingRequestPage.getContent().get(0).getTitle());
        assertEquals("new", trainingRequestPage.getContent().get(0).getStatus());
        assertEquals("alex", trainingRequestPage.getContent().get(0).getUser().getUsername());
    }

    @Test
    public void createTrainingRequest_thenUpdate_thenReturnPage() {
        Optional<IronUser> user = userRepository.findByUsername("alex");
        Optional<IronUser> trainer = userRepository.findByUsername("james");

        assertTrue(user.isPresent());
        assertTrue(trainer.isPresent());

        TrainingRequest trainingRequest = TrainingRequest.builder()
                .title("request")
                .description("description")
                .difficulty("beginner")
                .status("new")
                .bodyPart("arms")
                .created_at(LocalDateTime.now())
                .user(user.get())
                .build();

        entityManager.persist(trainingRequest);
        entityManager.flush();

        trainingRequest.setTrainer(trainer.get());
        trainingRequest.setStatus("in progress");

        trainingRequestRepository.save(trainingRequest);

        Pageable pageable = PageRequest.of(0, 100);
        Page<TrainingRequest> trainingRequestPage = trainingRequestRepository
                .findByTrainerIdAndStatusAndQuery(
                        trainer.get().getId(), "in progress", "request" , pageable);

        assertEquals(0, trainingRequestPage.getNumber());
        assertEquals(1, trainingRequestPage.getTotalElements());
        assertEquals(1, trainingRequestPage.getContent().size());
        assertEquals("request", trainingRequestPage.getContent().get(0).getTitle());
        assertEquals("in progress", trainingRequestPage.getContent().get(0).getStatus());
        assertEquals("alex", trainingRequestPage.getContent().get(0).getUser().getUsername());
        assertEquals("james", trainingRequestPage.getContent().get(0).getTrainer().getUsername());
    }
}
