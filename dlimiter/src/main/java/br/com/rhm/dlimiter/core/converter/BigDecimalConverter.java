package br.com.rhm.dlimiter.core.converter;

import java.math.BigDecimal;

class BigDecimalConverter extends StringConstructorConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal from(String value) {
		return (BigDecimal) convert(BigDecimal.class, value);
	}

	@Override
	public String to(BigDecimal t) {
		return t.toString();
	}

}