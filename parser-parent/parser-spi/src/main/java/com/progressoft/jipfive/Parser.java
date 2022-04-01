package com.progressoft.jipfive;

public interface Parser<T, S> {
	T parser(S line) throws IllegalFormatDataException;

}
