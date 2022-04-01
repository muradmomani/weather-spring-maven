package com.progressoft.jipfive;

public class CantMakeConnectionException extends RuntimeException {
    public CantMakeConnectionException(String s) {
        super(s);
    }

    public CantMakeConnectionException(String s, Exception e) {
        super(s,e);
    }
}
