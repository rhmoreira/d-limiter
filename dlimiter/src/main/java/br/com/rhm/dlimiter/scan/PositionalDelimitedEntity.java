package br.com.rhm.dlimiter.scan;

import br.com.rhm.dlimiter.core.formatter.Formatter;
import br.com.rhm.dlimiter.core.formatter.PositionalFormatter;
import br.com.rhm.dlimiter.core.parser.Parser;
import br.com.rhm.dlimiter.core.parser.PositionalParser;
import br.com.rhm.dlimiter.reflection.ClassHandler;

public class PositionalDelimitedEntity<T> extends AbstractDelimitedEntity<T> implements DelimitedEntityHandler<T>, DelimitedEntity<T> {

	private Parser<T> parser;
	private Formatter<T> formatter;
	
	public PositionalDelimitedEntity(Class<T> clazz, ClassHandler classHandler, Configuration conf) {
		super(clazz, classHandler, conf);
		this.parser = new PositionalParser<>(this);
		this.formatter = new PositionalFormatter<>(this);
	}

	@Override
	public Parser<T> getParser() {
		return parser;
	}

	@Override
	public Formatter<T> getFormatter() {
		return formatter;
	}
}
