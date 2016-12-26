package br.com.rhm.dlimiter.core;

import br.com.rhm.dlimiter.scan.DelimitedEntity;

class DelimiterImpl<T> extends AbstractDelimiter<T> {

	protected DelimiterImpl(DelimitedEntity<T> entity) {
		super(entity);
	}

	@Override
	public T parse(String value) {
		return parser.parse(value);
	}

	@Override
	public String format(T t) {
		return formatter.format(t);
	}
}
