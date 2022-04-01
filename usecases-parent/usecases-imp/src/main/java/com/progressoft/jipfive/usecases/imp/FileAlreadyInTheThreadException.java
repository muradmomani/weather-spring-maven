package com.progressoft.jipfive.usecases.imp;


public class FileAlreadyInTheThreadException extends RuntimeException {
    public FileAlreadyInTheThreadException(String s) {
        super(s);
    }
}
