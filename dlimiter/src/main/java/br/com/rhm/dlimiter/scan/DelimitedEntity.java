package br.com.rhm.dlimiter.scan;

import br.com.rhm.dlimiter.core.formatter.Formatter;
import br.com.rhm.dlimiter.core.parser.Parser;

public interface DelimitedEntity<T> {
	
	Class<T> getEntityClass();
	
	Parser<T> getParser();
	
	Formatter<T> getFormatter();

	Configuration getConfiguration();
}
