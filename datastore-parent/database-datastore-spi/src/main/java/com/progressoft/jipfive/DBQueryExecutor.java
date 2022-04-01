package com.progressoft.jipfive;


import java.util.Collection;

public interface DBQueryExecutor<T> {

    Collection<T> executeQuery(String query, Object... objects);
}
