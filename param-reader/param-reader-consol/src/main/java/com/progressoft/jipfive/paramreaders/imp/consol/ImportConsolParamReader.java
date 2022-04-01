package com.progressoft.jipfive.paramreaders.imp.consol;


import java.util.Scanner;

import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;

public class ImportConsolParamReader implements ImportParamReader {

    private Scanner in;

    public ImportConsolParamReader(Scanner in) {
        this.in = in;
    }

    @Override
    public String getFileName() {
        System.out.println("please insert file name : ");
        return in.nextLine().trim();
    }
}
