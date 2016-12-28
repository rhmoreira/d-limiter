package br.com.rhm.dlimiter.core.formatter;

import java.lang.reflect.Field;
import java.util.Arrays;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.TokenizedHeader;
import br.com.rhm.dlimiter.core.Delimiter;
import br.com.rhm.dlimiter.core.Delimiters;
import br.com.rhm.dlimiter.core.converter.Converter;
import br.com.rhm.dlimiter.core.converter.Converters;
import br.com.rhm.dlimiter.reflection.ClassHandler;
import br.com.rhm.dlimiter.reflection.MemberHandler.MethodHandler;
import br.com.rhm.dlimiter.reflection.ReflectionUtils;
import br.com.rhm.dlimiter.scan.Configuration;
import br.com.rhm.dlimiter.scan.DelimitedEntityHandler;
import br.com.rhm.dlimiter.scan.TokenDelimitedEntity;

public class TokenFormatter<T> implements Formatter<T> {

	private TokenDelimitedEntity<T> entity;
	private Configuration conf;
	private ClassHandler clHandler;
	
	public TokenFormatter(DelimitedEntityHandler<T> entity) {
		super();
		this.entity = (TokenDelimitedEntity<T>) entity;
		this.conf = entity.getConfiguration();
		this.clHandler = entity.getClassHandler();
	}

	@Override
	public String format(T t){
		StringBuilder sb = new StringBuilder();
		
		Field field = null;
		try{
			for (Integer index: entity.getSortedIndexSet()){
				field = entity.getFieldForIndex(index);
				String value = getFieldStringValue(t, field);
				
				sb.append(value).append(conf.getDelimiterToken());
			}
			
			if (sb.length() > 0)
				sb.setLength(sb.length() - 1);
			
			return sb.toString();
		}catch (Exception e) {
			throw new DelimiterException("Error processing property [" + field.getName() + "] from Delimited Entity [" + t.getClass().getName() + "]", e);
		}
	}
	
	private String getDependencyValue(Object value) {
		Delimiter<Object> delimiter = (Delimiter<Object>) Delimiters.getInstance(value.getClass());
		String stringValue = delimiter.format(value);
		return stringValue;
	}

	private String getFieldStringValue(T target, Field field) throws Exception{
		MethodHandler fieldGetter = clHandler.getMemberHandler().getterMethodForField(field);
		Object value = fieldGetter.invoke(target, ReflectionUtils.EMPTY_ARGS);
		
		if (value == null)
			return "";
		else{
			String stringValue = null;
			if (clHandler.getDependencyMapper().isDependency(field.getType()))
				stringValue = getDependencyValue(value);
			else
				stringValue = convert(value);
			
			return stringValue;
		}
	}
	
	private String convert(Object value){
		Converter<Object> converter = (Converter<Object>) Converters.getConverterFor(value.getClass());
		String stringValue = converter.to(value);
		
		return stringValue;
	}
	
	@Override
	public String formatHeader() throws DelimiterException {
		Class<?> handledClass = clHandler.getHandledClass();
		TokenizedHeader header = handledClass.getAnnotation(TokenizedHeader.class);
		if (header != null){
			String[] headerValues = header.value();
			StringBuilder sb = new StringBuilder();
			Arrays
				.stream(headerValues)
				.forEach(h -> sb.append(h).append(conf.getDelimiterToken()));
			
			sb.setLength(sb.length() - 1);
			return sb.append(Delimiter.LB_CR).toString();
		}
		return "";
	}
	
}
