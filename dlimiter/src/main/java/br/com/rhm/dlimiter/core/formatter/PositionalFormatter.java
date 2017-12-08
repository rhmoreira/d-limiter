package br.com.rhm.dlimiter.core.formatter;

import java.lang.reflect.Field;
import java.util.Collection;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.Position;
import br.com.rhm.dlimiter.reflection.ClassHandler;
import br.com.rhm.dlimiter.scan.DelimitedEntityHandler;
import br.com.rhm.dlimiter.scan.PositionalDelimitedEntity;

public class PositionalFormatter<T> extends AbstractFormatter<T> implements Formatter<T> {

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
				String value = getStringValue(entity, t, field);
				Position position = field.getAnnotation(Position.class);
				sorter.addValue(new PositionalValue(position, value));
			}
			
			sorter.sort();
			return sorter.toString();
			
		}catch (Exception e) {
			throw new DelimiterException("Error processing property [" + fieldAux.getName() + "] from Delimited Entity [" + t.getClass().getName() + "]", e);
		}
	}
	
	@Override
	public String formatHeader() throws DelimiterException {
		return "";
	}
}
