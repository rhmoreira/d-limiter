package br.com.rhm.dlimiter.core.converter;

public interface Converter<T> {

	T from(String value);
	String toString(T t);
}
