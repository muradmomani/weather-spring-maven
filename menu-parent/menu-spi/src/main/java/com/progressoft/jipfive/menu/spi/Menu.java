package com.progressoft.jipfive.menu.spi;



import com.progressoft.jipfive.Action;

public interface Menu {
    void addOption(int key, String desc, Action action);

    void displayOptions();

    void executeOption(int key);

}
