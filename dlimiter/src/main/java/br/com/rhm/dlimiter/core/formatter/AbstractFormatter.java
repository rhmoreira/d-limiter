package br.com.rhm.dlimiter.core.formatter;

import java.lang.reflect.Field;

import br.com.rhm.dlimiter.core.Delimiter;
import br.com.rhm.dlimiter.core.Delimiters;
import br.com.rhm.dlimiter.core.converter.Converter;
import br.com.rhm.dlimiter.core.converter.Converters;
import br.com.rhm.dlimiter.reflection.ClassHandler;
import br.com.rhm.dlimiter.reflection.DependencyMapper;
import br.com.rhm.dlimiter.reflection.MemberHandler;
import br.com.rhm.dlimiter.reflection.MemberHandler.MethodHandler;
import br.com.rhm.dlimiter.reflection.ReflectionUtils;
import br.com.rhm.dlimiter.scan.AbstractDelimitedEntity;

public abstract class AbstractFormatter<T> implements Formatter<T> {

	protected String getStringValue(AbstractDelimitedEntity<T> entity, T target, Field field) throws Exception{
		
		ClassHandler clHandler = entity.getClassHandler();
		DependencyMapper dependencyMapper = clHandler.getDependencyMapper();
		MemberHandler memberHandler = clHandler.getMemberHandler();
		
		MethodHandler fieldGetter = memberHandler.getterMethodForField(field);
		Object value = fieldGetter.invoke(target, ReflectionUtils.EMPTY_ARGS);
		
		
		if (value == null)
			return "";
		else{
			String stringValue = null;
			if (dependencyMapper.isDependency(field.getType()))
				stringValue = getDependencyValue(field, value);
			else
				stringValue = convert(field, value);
			
			return stringValue;
		}
	}

	protected String convert(Field field, Object value) {
		Converter<Object> converter = (Converter<Object>) Converters.getConverterFor(field.getType());
		return converter.toString(value);
	}

	protected String getDependencyValue(Field field, Object value) {
		Delimiter<Object> delimiter = (Delimiter<Object>) Delimiters.getInstance(field.getType());
		return delimiter.format(value);
	}
}
