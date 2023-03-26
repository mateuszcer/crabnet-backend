package com.mateuszcer.socialmediaapp.exceptions;

public class UserAlreadyExistException extends Exception {
        public UserAlreadyExistException(String errorMessage) {
            super(errorMessage);
        }

}
