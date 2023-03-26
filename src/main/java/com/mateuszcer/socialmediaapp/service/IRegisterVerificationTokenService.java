package com.mateuszcer.socialmediaapp.service;

import com.mateuszcer.socialmediaapp.model.RegisterVerificationToken;
import com.mateuszcer.socialmediaapp.model.User;

public interface IRegisterVerificationTokenService {

    void createVerificationToken(User user, String token);

    RegisterVerificationToken getVerificationToken(String VerificationToken);
}
