package br.com.rhm.dlimiter.core.converter;

class ShortConverter extends StringConstructorConverter  implements Converter<Short> {

	@Override
	public Short from(String value) {
		return convert(Short.class, value);
	}

	@Override
	public String toString(Short s) {
		return s.toString();
	}

}
