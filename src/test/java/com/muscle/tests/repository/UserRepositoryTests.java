package com.muscle.tests.repository;

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

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    IronUser user;

    @Before
    public void setupUser() {
        Role role = new Role(1L, "USER");
        user = IronUser.builder()
                .username("alex")
                .email("alex@gmail.com")
                .password("Alex#123")
                .icon("profile-picture/default/icon.png")
                .locked(false)
                .enabled(true)
                .roles(Collections.singletonList(role))
                .build();
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        entityManager.persist(user);
        entityManager.flush();

        Optional<IronUser> found = userRepository.findByUsername(user.getUsername());

        assertTrue(found.isPresent());
        assertEquals(found.get().getUsername(), user.getUsername());
    }

    @Test
    public void whenFindUsernameContains_thenReturnList_withOneElement() {
        entityManager.persist(user);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 100);
        Page<IronUser> userPage = userRepository.findByUsernameContainsOrderByUsernameAsc(pageable, user.getUsername());

        assertEquals(0, userPage.getNumber());
        assertEquals(1L, userPage.getTotalElements());
        assertEquals(1, userPage.getContent().size());
        assertEquals(user.getUsername(), userPage.getContent().get(0).getUsername());
    }

    @Test
    public void whenFindUsernameContains_thenReturnEmptyList() {
        Pageable pageable = PageRequest.of(0, 100);
        Page<IronUser> userPage = userRepository.findByUsernameContainsOrderByUsernameAsc(pageable, "alex");

        assertEquals(0, userPage.getNumber());
        assertEquals(0L, userPage.getTotalElements());
        assertEquals(0, userPage.getContent().size());
    }
}
