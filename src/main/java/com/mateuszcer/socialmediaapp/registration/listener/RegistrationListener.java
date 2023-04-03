package com.mateuszcer.socialmediaapp.registration.listener;

import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.registration.OnRegistrationCompleteEvent;
import com.mateuszcer.socialmediaapp.service.RegisterVerificationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.util.UUID;

@Component
public class RegistrationListener implements
        ApplicationListener<OnRegistrationCompleteEvent> {

    private final RegisterVerificationTokenService tokenService;

    private final JavaMailSender mailSender;

    @Value("${crabnet.hostname}")
    private String hostName;

    public RegistrationListener(RegisterVerificationTokenService tokenService,
                                JavaMailSender mailSender) {
        this.tokenService = tokenService;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user;
        user = event.getUser();
        String token;
        token = UUID.randomUUID().toString();
        tokenService.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = hostName + event.getAppUrl() + "/auth/registrationConfirm?token=" + token;


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("Complete registration by clicking link below:" + "\r\n" + confirmationUrl);
        mailSender.send(email);
    }
}