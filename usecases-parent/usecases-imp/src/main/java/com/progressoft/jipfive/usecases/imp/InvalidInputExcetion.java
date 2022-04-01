package com.progressoft.jipfive.usecases.imp;


public class InvalidInputExcetion extends RuntimeException {
    public InvalidInputExcetion(String message) {
        super( message );
    }
}
