package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.model.RegisterVerificationToken;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.service.RegisterVerificationTokenService;
import com.mateuszcer.socialmediaapp.service.UserService;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/auth/registrationConfirm")
    public ResponseEntity<?> confirmRegistration
            (WebRequest request, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        RegisterVerificationToken verificationToken = tokenService.getVerificationToken(token);
        if (verificationToken == null) {
            ResponseEntity
                    .badRequest()
                    .body("Error");
        }

        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return ResponseEntity
                    .badRequest()
                    .body("Confirmation token expired.");
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return ResponseEntity.ok("Account activated!");
    }
}
