package br.com.rhm.dlimiter.core.converter;

class FloatConverter extends StringConstructorConverter  implements Converter<Float> {

	@Override
	public Float from(String value) {
		return convert(Float.class, value);
	}

	@Override
	public String toString(Float t) {
		return t.toString();
	}

}
