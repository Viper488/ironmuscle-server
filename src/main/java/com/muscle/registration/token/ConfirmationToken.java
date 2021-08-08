package com.muscle.registration.token;

import com.muscle.user.repository.IronUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter

@Entity
@Data
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "iron_user_id"
    )
    private IronUser ironUser;
}
