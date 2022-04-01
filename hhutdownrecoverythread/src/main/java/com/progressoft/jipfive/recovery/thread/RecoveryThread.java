package com.progressoft.jipfive.recovery.thread;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Queue;

public class RecoveryThread implements RecoveryParent {

	private Queue<String> filesInTheThread;
	private Path dirNAme;

	@Override
	public void run() {
		Path dirPath = Paths.get(getDirNAme().toString() + "/" + "backUp.txt");
		try {
			if (!Files.exists(dirPath))
				Files.createFile(dirPath);
			Files.write(dirPath, filesInTheThread);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setListOfFile(Queue<String> list) {
		filesInTheThread = list;
	}

	public Path getDirNAme() {
		return dirNAme;
	}

	public void setDirNAme(Path dirNAme) {
		this.dirNAme = dirNAme;
	}

}
