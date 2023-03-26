package com.mateuszcer.socialmediaapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Calendar;
import java.util.Date;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterVerificationToken {
    private static final int EXPIRATION = 60 * 24; // 24 hours

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;

    private Date expiryDate;

    private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, RegisterVerificationToken.EXPIRATION);
        return new Date(cal.getTime().getTime());
    }

    public RegisterVerificationToken(User user, String token) {
        this.user = user;
        this.token = token;

        this.expiryDate = calculateExpiryDate();
    }
}
