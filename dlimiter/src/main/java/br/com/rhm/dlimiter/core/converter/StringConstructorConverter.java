package br.com.rhm.dlimiter.core.converter;

import java.lang.reflect.Constructor;

import br.com.rhm.dlimiter.DelimiterException;

abstract class StringConstructorConverter {

	private static final Class<?>[] ARGS_TYPE = {String.class};
	
	public <T> T convert(Class<T> classType, String value){
		try {
			Constructor<T> constructor = classType.getDeclaredConstructor(ARGS_TYPE);
			return constructor.newInstance(value);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new DelimiterException("Class [" + classType.getName() + "] has no String parameter constructor");
		} catch (Exception e) {
			throw new DelimiterException("The String parameter constructor of the Class [" + classType.getName() + "] may not be visible");
		}
	}
}
