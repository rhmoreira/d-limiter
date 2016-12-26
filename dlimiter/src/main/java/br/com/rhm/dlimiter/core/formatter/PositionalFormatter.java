package br.com.rhm.dlimiter.core.formatter;

import java.lang.reflect.Field;
import java.util.Collection;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.Position;
import br.com.rhm.dlimiter.core.Delimiter;
import br.com.rhm.dlimiter.core.Delimiters;
import br.com.rhm.dlimiter.core.converter.Converter;
import br.com.rhm.dlimiter.core.converter.Converters;
import br.com.rhm.dlimiter.reflection.ClassHandler;
import br.com.rhm.dlimiter.reflection.DependencyMapper;
import br.com.rhm.dlimiter.reflection.MemberHandler;
import br.com.rhm.dlimiter.reflection.MemberHandler.MethodHandler;
import br.com.rhm.dlimiter.reflection.ReflectionUtils;
import br.com.rhm.dlimiter.scan.DelimitedEntityHandler;
import br.com.rhm.dlimiter.scan.PositionalDelimitedEntity;

public class PositionalFormatter<T> implements Formatter<T> {

	private PositionalDelimitedEntity<T> entity;
	
	public PositionalFormatter(DelimitedEntityHandler<T> entity) {
		super();
		this.entity = (PositionalDelimitedEntity<T>) entity;
	}
	
	@Override
	public String format(T t) throws DelimiterException {
		if (t == null)
			return null;
		
		ClassHandler clHandler = entity.getClassHandler();
		Collection<Field> fields = clHandler.getScannedFields();
		Field fieldAux = null;
		
		try{
			
			PositionalValueSorter sorter = new PositionalValueSorter();
			
			for (Field field: fields){
				fieldAux = field;
				String value = getStringValue(t, field);
				Position position = field.getAnnotation(Position.class);
				sorter.addValue(new PositionalValue(position, value));
			}
			
			sorter.sort();
			return sorter.toString();
			
		}catch (Exception e) {
			throw new DelimiterException("Error processing property [" + fieldAux.getName() + "] from Delimited Entity [" + t.getClass().getName() + "]", e);
		}
	}
	
	private String getStringValue(T target, Field field) throws Exception{
		ClassHandler clHandler = entity.getClassHandler();
		DependencyMapper dependencyMapper = clHandler.getDependencyMapper();
		MemberHandler memberHandler = clHandler.getMemberHandler();
		
		MethodHandler fieldGetter = memberHandler.getterMethodForField(field);
		Object value = fieldGetter.invoke(target, ReflectionUtils.EMPTY_ARGS);
		
		
		if (dependencyMapper.isDependency(field.getType()))
			return getDependencyValue(field, value);
		else
			return convert(field, value);
	}

	private String convert(Field field, Object value) {
		Converter<Object> converter = (Converter<Object>) Converters.getConverterFor(field.getType());
		return converter.to(value);
	}

	private String getDependencyValue(Field field, Object value) {
		Delimiter<Object> delimiter = (Delimiter<Object>) Delimiters.getInstance(field.getType());
		return delimiter.format(value);
	}

}
