package br.com.rhm.dlimiter.core.converter;

class IntegerConverter extends StringConstructorConverter  implements Converter<Integer> {

	@Override
	public Integer from(String value) {
		return convert(Integer.class, value);
	}

	@Override
	public String toString(Integer t) {
		return t.toString();
	}

}
