package com.mateuszcer.socialmediaapp.controller;

import com.mateuszcer.socialmediaapp.exceptions.UserAlreadyExistException;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.payload.request.LoginRequest;
import com.mateuszcer.socialmediaapp.payload.request.SignupRequest;
import com.mateuszcer.socialmediaapp.payload.response.JwtResponse;
import com.mateuszcer.socialmediaapp.registration.OnRegistrationCompleteEvent;
import com.mateuszcer.socialmediaapp.security.JwtUtil;
import com.mateuszcer.socialmediaapp.security.services.UserDetailsImpl;
import com.mateuszcer.socialmediaapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
@RequestMapping("/auth")
@CrossOrigin("http://127.0.0.1:5173")
public class AuthController {


    private final UserService userService;

    private final DaoAuthenticationProvider daoAuthenticationProvider;

    private final JwtUtil jwtUtil;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public AuthController(UserService userService, DaoAuthenticationProvider daoAuthenticationProvider, JwtUtil jwtUtil, ApplicationEventPublisher applicationEventPublisher) {
        this.userService = userService;
        this.daoAuthenticationProvider = daoAuthenticationProvider;
        this.jwtUtil = jwtUtil;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @PostMapping(path="/signin")
    public ResponseEntity<JwtResponse> signinUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication;
        authentication = daoAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.createToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername()));
    }
    @PostMapping(path="/signup")
    public ResponseEntity<?> signupUser(HttpServletRequest request, @RequestBody @Valid SignupRequest signupRequest,
                                        BindingResult result) {


        if(result.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid registration form");
        }

        try {

            User newUser = userService.registerUser(signupRequest);
            String appUrl = request.getContextPath();
            Locale locale = request.getLocale();

            applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(newUser, appUrl,
                    locale));
            return ResponseEntity.ok("User registered successfully!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity
                    .badRequest()
                    .body("Unexpected problem while sending confirmation email. Please try again " +
                            "and contact developers.");
        }
    }

}
