package br.com.rhm.dlimiter.core.parser;

import br.com.rhm.dlimiter.DelimiterException;

public interface Parser<T> {

	T parse(String value) throws DelimiterException;
}
