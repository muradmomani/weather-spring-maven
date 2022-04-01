package com.progressoft.jipfive.usecases.spi;


import java.util.List;
import java.util.Queue;

import com.progressoft.jipfive.ImportReport;
import com.progressoft.jipfive.paramreaders.spi.ImportParamReader;


public interface ImportUseCase {

    void importGsodFile(ImportParamReader reader);

    List<ImportReport> gsodImportJobStatus();

    List<String> getAvilableFiles();

	Queue<String> getFilesInTheThread();
}
