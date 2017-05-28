package br.com.rhm.dlimiter.core.converter;

class LongConverter extends StringConstructorConverter  implements Converter<Long> {

	@Override
	public Long from(String value) {
		return convert(Long.class, value);
	}

	@Override
	public String toString(Long t) {
		return t.toString();
	}

}
