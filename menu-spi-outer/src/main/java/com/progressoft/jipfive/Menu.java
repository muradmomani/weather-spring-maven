package com.progressoft.jipfive;

public interface Menu {
	void addOption(int key, String desc, Action action);

	void displayOptions();

	void executeOption(int key);

}
