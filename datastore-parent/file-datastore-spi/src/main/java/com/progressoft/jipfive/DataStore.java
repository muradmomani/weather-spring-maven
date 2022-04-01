package com.progressoft.jipfive;

import java.util.Collection;

public interface DataStore<T> {

    String getDirName();

    Collection<T> readObjects(String file);
}
