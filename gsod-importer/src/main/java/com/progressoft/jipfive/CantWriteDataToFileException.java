package com.progressoft.jipfive;


import java.io.IOException;

@SuppressWarnings("serial")
public class CantWriteDataToFileException extends RuntimeException {
    public CantWriteDataToFileException(String cant_write_data_to_file, IOException ex) {
        super(cant_write_data_to_file,ex);
    }
}
