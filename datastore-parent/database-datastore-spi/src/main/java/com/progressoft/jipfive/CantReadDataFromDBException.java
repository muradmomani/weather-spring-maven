package com.progressoft.jipfive;


public class CantReadDataFromDBException extends RuntimeException {
    public CantReadDataFromDBException(String s,Exception e) {
        super(s,e);
    }
}
