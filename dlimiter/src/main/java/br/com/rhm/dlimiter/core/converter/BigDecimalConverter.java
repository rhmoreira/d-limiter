package br.com.rhm.dlimiter.core.converter;

import java.math.BigDecimal;

class BigDecimalConverter extends StringConstructorConverter implements Converter<BigDecimal> {

	@Override
	public BigDecimal from(String value) {
		value = replaceChars(value);
		return (BigDecimal) convert(BigDecimal.class, value);
	}

	@Override
	public String toString(BigDecimal t) {
		return t.toString();
	}
	
	private String replaceChars(String value) {
		if (value.indexOf(",") != -1){
			if (value.indexOf(".") != -1)
				value = value.replaceAll("\\.", "");
			
			return value.replaceAll(",", ".");
		}else
			return value;
	}

}
