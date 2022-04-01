package com.progressoft.jipfive.consol.operation.imp;


import java.util.List;
import java.util.Scanner;

import com.progressoft.jipfive.ImportReport;
import com.progressoft.jipfive.consol.operations.ImportAndStatusOperation;
import com.progressoft.jipfive.paramreaders.imp.consol.ImportConsolParamReader;
import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;
import com.progressoft.jipfive.usecases.spi.ImportUseCase;

public class ImportAndStatusOperations implements ImportAndStatusOperation {

    private ImportUseCase importUseCase;

    public ImportAndStatusOperations(ImportUseCase importUseCase) {
        this.importUseCase = importUseCase;
    }

    @Override
    public void importGsodFile() {
        List<String> avilableFiles = getAvilableFiles();
        avilableFiles.forEach(System.out::println);
        ImportParamReader importParamReader = new ImportConsolParamReader(new Scanner(System.in));
        importUseCase.importGsodFile(importParamReader);
    }

    @Override
    public void gsodImportJobStatus() {
        List<ImportReport> importReports = importUseCase.gsodImportJobStatus();
        for (ImportReport rebort : importReports) {
            System.out.println(rebort);
        }
    }

    @Override
    public List<String> getAvilableFiles() {
        return importUseCase.getAvilableFiles();
    }
}
