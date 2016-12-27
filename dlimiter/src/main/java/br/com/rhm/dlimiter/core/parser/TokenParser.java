package br.com.rhm.dlimiter.core.parser;

import java.lang.reflect.Field;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.Index;
import br.com.rhm.dlimiter.core.Delimiter;
import br.com.rhm.dlimiter.core.Delimiters;
import br.com.rhm.dlimiter.core.converter.Converter;
import br.com.rhm.dlimiter.core.converter.Converters;
import br.com.rhm.dlimiter.reflection.ClassHandler;
import br.com.rhm.dlimiter.reflection.DependencyMapper;
import br.com.rhm.dlimiter.scan.Configuration;
import br.com.rhm.dlimiter.scan.DelimitedEntityHandler;
import br.com.rhm.dlimiter.scan.TokenDelimitedEntity;

public class TokenParser<T> implements Parser<T> {

	private TokenDelimitedEntity<T> entity;
	private Configuration conf;
	
	public TokenParser(DelimitedEntityHandler<T> entity) {
		super();
		this.entity = (TokenDelimitedEntity<T>) entity;
		this.conf = entity.getConfiguration();
	}

	@Override
	public T parse(String value){
		String[] values = value.split(conf.getDelimiterToken());
		T entityInstance = entity.getClassHandler().newInstance();
		
		for (int i = 0; i < values.length; i++){
			applyValue(i, values[i], entityInstance);
		}
		
		return entityInstance;
	}
	
	private void applyValue(int index, String value, T entityInstance){
		Field field = entity.getFieldForIndex(index);
		
		if (field == null || value.equals(""))
			return ;
		
		Index annotation = field.getAnnotation(Index.class);		
		if (annotation.trim())
			value = value.trim();
		
		ClassHandler classHandler = entity.getClassHandler();
		DependencyMapper dependencyMapper = classHandler.getDependencyMapper();
		
		try{
			if (dependencyMapper.isDependency(field.getType())){
				Delimiter<?> dependencyDelimiter = Delimiters.getInstance(field.getType());
				Object dependencyInstance = dependencyDelimiter.parse(value);
				
				classHandler.getInjector().inject(entityInstance, dependencyInstance, field);
			}else{
				Converter<?> converter = Converters.getConverterFor(field.getType());
				Object result = converter.from(value);
				classHandler.getInjector().inject(entityInstance, result, field);
			}
		}catch (Exception e) {
			throw new DelimiterException("Error apllying value [" + value + "] in the field [" + field.getType() + "]");
		}
	}
}
