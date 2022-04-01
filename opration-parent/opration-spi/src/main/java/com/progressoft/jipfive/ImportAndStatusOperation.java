package com.progressoft.jipfive;

import java.util.List;

import com.progressoft.jipfive.importGsod.requests.ImportGsodFileRequest;

public interface ImportAndStatusOperation {
	
	void importGsodFile(ImportGsodFileRequest request);

	List<ImportReport> gsodImportJobStatus();

	List<String> getAvilableFiles();
}
