package com.progressoft.jipfive;


//  renamed the class to be more logical with using the generic
// it was ConnectionFactory<T>
public interface Factory<T> {
    T create();
}
