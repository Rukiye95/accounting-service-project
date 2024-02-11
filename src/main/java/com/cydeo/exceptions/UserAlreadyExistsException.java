package com.cydeo.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException() {
        super("A user with this email already exists. Please try with a different email.");
    }
}
