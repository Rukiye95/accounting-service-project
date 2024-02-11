package com.cydeo.exceptions;

public class CompanyAlreadyExistsException extends RuntimeException{
    public CompanyAlreadyExistsException() {
        super("Company with this title is already exists. Please try with a different title.");
    }
}
