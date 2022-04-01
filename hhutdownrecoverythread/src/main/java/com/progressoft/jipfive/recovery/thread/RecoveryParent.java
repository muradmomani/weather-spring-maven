package com.progressoft.jipfive.recovery.thread;

import java.util.Queue;

public interface RecoveryParent extends Runnable {
	void setListOfFile(Queue<String> list);
}
