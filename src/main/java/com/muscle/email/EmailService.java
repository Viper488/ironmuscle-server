package com.muscle.email;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService implements EmailSender{
    private final static String FAILED_TO_SEND_EMAIL = "Failed to send an email";
    private final static String EMAIL_TITLE = "Confirm your email";
    private final static String EMAIL_FROM = "noreply@iron.muscles.com";
    private final static String ENCODING = "utf-8";

    private final JavaMailSender mailSender;

    @Override
    @Async
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, ENCODING);
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject(EMAIL_TITLE);
            helper.setFrom(EMAIL_FROM);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.info(FAILED_TO_SEND_EMAIL, e);
            throw new IllegalStateException(FAILED_TO_SEND_EMAIL);
        }
    }
}
