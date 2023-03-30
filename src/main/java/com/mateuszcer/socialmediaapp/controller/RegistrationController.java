package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.RegisterVerificationToken;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.registration.OnRegistrationCompleteEvent;
import com.mateuszcer.socialmediaapp.service.RegisterVerificationTokenService;
import com.mateuszcer.socialmediaapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Locale;


@RestController
public class RegistrationController {

    private final UserService userService;

    private final RegisterVerificationTokenService tokenService;

    public RegistrationController(UserService userService, RegisterVerificationTokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }




}
