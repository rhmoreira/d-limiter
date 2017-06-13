package br.com.rhm.dlimiter.core.converter;

public class StringConverter extends StringConstructorConverter  implements Converter<String> {

	@Override
	public String from(String value) {
		return value;
	}

	@Override
	public String toString(String t) {
		return t;
	}

}
