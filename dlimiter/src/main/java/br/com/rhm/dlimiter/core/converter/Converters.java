package br.com.rhm.dlimiter.core.converter;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.rhm.dlimiter.DelimiterException;

public class Converters {

	private static Map<Class<?>, Converter<?>> converterMap = new HashMap<>();
	
	static {
		converterMap.put(String.class, new StringConverter());
		converterMap.put(Short.class, new ShortConverter());
		converterMap.put(Integer.class, new IntegerConverter());
		converterMap.put(Long.class, new LongConverter());
		converterMap.put(Float.class, new FloatConverter());
		converterMap.put(Double.class, new DoubleConverter());
		converterMap.put(BigDecimal.class, new BigDecimalConverter());

		DateConverter dateConverter = new DateConverter();
		converterMap.put(Date.class, dateConverter);
		converterMap.put(Timestamp.class, dateConverter);
		converterMap.put(java.sql.Date.class, dateConverter);
	}
	
	public static <T> void registerConverter(Converter<T> converter, Class<T> clazz){
		converterMap.put(clazz, converter);
	}
	
	public static <T> Converter<T> getConverterFor(Class<T> clazz){
		Converter<?> converter = converterMap.get(clazz);
		if (converter == null)
			throw new DelimiterException("No converter found for [" + clazz.getName() + "]");
		else
			return (Converter<T>) converter;
	}
}
