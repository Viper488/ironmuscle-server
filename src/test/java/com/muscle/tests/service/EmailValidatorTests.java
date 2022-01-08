package com.muscle.tests.service;

import com.muscle.user.service.EmailValidator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailValidatorTests {

    @Autowired
    EmailValidator emailValidator;

    @Test
    public void correctMail_1() {
        assertTrue(emailValidator.test("example@mail.com"));
    }

    @Test
    public void correctMail_2() {
        assertTrue(emailValidator.test("example@mail.something.com"));
    }

    @Test
    public void correctMail_3() {
        assertTrue(emailValidator.test("example.something@mail.com"));
    }


    @Test
    public void correctMail_4() {
        assertTrue(emailValidator.test("example@mail.pl"));
    }

    @Test
    public void incorrectMail_1() {
        assertFalse(emailValidator.test("examplemail.com"));
    }

    @Test
    public void incorrectMail_2() {
        assertFalse(emailValidator.test("example@mailcom"));
    }

    @Test
    public void incorrectMail_3() {
        assertFalse(emailValidator.test("@mail.com"));
    }

    @Test
    public void emptyMail_1() {
        assertFalse(emailValidator.test(""));
    }

}
