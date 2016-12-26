package br.com.rhm.dlimiter.core.converter;

class ShortConverter extends StringConstructorConverter  implements Converter<Short> {

	@Override
	public Short from(String value) {
		return convert(Short.class, value);
	}

	@Override
	public String to(Short s) {
		return s.toString();
	}

}
