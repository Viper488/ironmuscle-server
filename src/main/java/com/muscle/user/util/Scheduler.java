package com.muscle.user.util;

import com.muscle.trainings.entity.Point;
import com.muscle.trainings.repository.PointRepository;
import com.muscle.user.entity.ConfirmationToken;
import com.muscle.user.entity.IronUser;
import com.muscle.user.entity.PasswordToken;
import com.muscle.user.repository.ConfirmationTokenRepository;
import com.muscle.user.repository.PasswordTokenRepository;
import com.muscle.user.repository.UserBadgesRepository;
import com.muscle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordTokenRepository passwordTokenRepository;
    private final PointRepository pointRepository;
    private final UserBadgesRepository userBadgesRepository;

    @Transactional
    //@Scheduled(cron = "0/20 * * ? * *") //Every 20 seconds
    @Scheduled(cron = "0 0 12 ? * SUN") //At 12:00:00pm, on every Sunday, every month
    public void scheduleDeleteNotEnabledUsers() {
        List<ConfirmationToken> tokens = confirmationTokenRepository.findByExpiresAtBeforeAndConfirmedAtIsNull(LocalDateTime.now());
        log.info("Scheduled: " + tokens.size() + " expired unconfirmed tokens found!");
        for (ConfirmationToken token:tokens
             ) {
            IronUser user = token.getIronUser();
            Optional<PasswordToken> passwordToken = passwordTokenRepository.findByIronUserUsername(user.getUsername());
            passwordToken.ifPresent(value -> passwordTokenRepository.deleteById(value.getId()));
            confirmationTokenRepository.deleteById(token.getId());
            userRepository.deleteById(user.getId());

            log.info("Scheduled: Deleted user " + user.getUsername() + " with unconfirmed token!");
        }
    }

    @Transactional
    //@Scheduled(cron = "0/20 * * ? * *") //Every 20 seconds
    @Scheduled(cron = "0 0 0 1 * ?")//At 00:00:00am, on the 1st day, every month
    public void schedulePointsReset() {
        pointRepository.resetPoints();

        log.info("Scheduled: reset points to 0");
    }

    @Transactional
    //@Scheduled(cron = "0/20 * * ? * *") //Every 20 seconds
    @Scheduled(cron = "0 0 0 1 * ?")//At 00:00:00am, on the 1st day, every month
    public void scheduleBadgesReset() {
        userBadgesRepository.resetBadges();

        log.info("Scheduled: reset badges");
    }
}
