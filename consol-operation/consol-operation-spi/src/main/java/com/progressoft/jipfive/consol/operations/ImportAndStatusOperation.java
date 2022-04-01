package com.progressoft.jipfive.consol.operations;


import java.util.List;

public interface ImportAndStatusOperation {
    void importGsodFile();

    //option 6
    void gsodImportJobStatus();

    List<String> getAvilableFiles();

}
