package br.com.rhm.dlimiter.core.formatter;

import br.com.rhm.dlimiter.DelimiterException;

public interface Formatter<T> {

	String format(T t) throws DelimiterException;
}
