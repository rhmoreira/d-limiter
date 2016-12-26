package br.com.rhm.dlimiter.core.parser;

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
import br.com.rhm.dlimiter.scan.PositionalDelimitedEntity;

public class PositionalParser<T> implements Parser<T> {

	private PositionalDelimitedEntity<T> entity;
	
	public PositionalParser(PositionalDelimitedEntity<T> entity) {
		super();
		this.entity = entity;
	}

	@Override
	public T parse(String value) throws DelimiterException {
		if (value == null || value.length() < 1)
			return null;
		
		T entityInstance = entity.getClassHandler().newInstance();
		
		applyValues(entityInstance, value);
		
		return entityInstance;
	}

	private void applyValues(T entityInstance, String value) {
		ClassHandler clHandler = entity.getClassHandler();
		DependencyMapper dependencyMapper = clHandler.getDependencyMapper();
		
		Field fieldAux = null;
		try{
			Collection<Field> fields = clHandler.getScannedFields();
			for (Field field: fields){
				fieldAux = field;
				Position position = getPosition(field);
				
				if (value.length() < position.end())
					continue;
				
				String subStringValue = value.substring(position.start() -1, position.end());
				
				Object injectee = null;
				if (dependencyMapper.isDependency(field.getType())){
					Delimiter<?> delimiter = Delimiters.getInstance(field.getType());
					injectee = delimiter.parse(subStringValue);
				}else{
					if (position.trim())
						subStringValue = subStringValue.trim();
					
					Converter<?> converter = Converters.getConverterFor(field.getType());
					injectee = converter.from(subStringValue);
				}
				
				clHandler.getInjector().inject(entityInstance, injectee, field);
			}
		
		}catch (Exception e) {
			throw new DelimiterException("Error apllying value [" + value + "] in the field [" + fieldAux.getType() + "]");
		}
	}
	
	private Position getPosition(Field field){
		return field.getAnnotation(Position.class);
	}
}
