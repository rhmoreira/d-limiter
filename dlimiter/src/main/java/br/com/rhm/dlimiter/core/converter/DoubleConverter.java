package br.com.rhm.dlimiter.core.converter;

class DoubleConverter extends StringConstructorConverter  implements Converter<Double> {

	@Override
	public Double from(String value) {
		return convert(Double.class, value);
	}

	@Override
	public String toString(Double t) {
		return t.toString();
	}

}
