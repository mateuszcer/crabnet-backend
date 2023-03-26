package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.RegisterVerificationToken;
import com.mateuszcer.socialmediaapp.model.User;
import com.mateuszcer.socialmediaapp.repository.RegisterVerificationTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RegisterVerificationTokenService implements IRegisterVerificationTokenService {
    private final RegisterVerificationTokenRepository tokenRepository;

    public RegisterVerificationTokenService(RegisterVerificationTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void createVerificationToken(User user, String token) {
        RegisterVerificationToken myToken = new RegisterVerificationToken(user, token);
        tokenRepository.save(myToken);
    }

    @Override
    public RegisterVerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }
}
