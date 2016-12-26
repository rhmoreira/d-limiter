package br.com.rhm.dlimiter.scan;

import java.util.HashMap;
import java.util.Map;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.Delimited;
import br.com.rhm.dlimiter.reflection.ClassHandler;

public class DelimiterScanner {
	
	private static Map<Class<?>, DelimitedEntity<?>> entityCacheMap = new HashMap<>();

	public static void scan(Class... classes){
		if (classes != null)
			for (Class clazz: classes)
				scan(clazz, false);
	}
	
	public static <T> DelimitedEntity<T> scan(Class<T> clazz){
		return scan(clazz, false);
	}
	
	public static <T> DelimitedEntity<T> scan(Class<T> clazz, boolean replace){
		Configuration conf = new Configuration();
		conf.setDelimiterToken(";");
		return scan(clazz, conf, replace);
	}
	
	public static <T> DelimitedEntity<T> scan(Class<T> clazz, Configuration conf){
		return scan(clazz, conf, false);
	}
	
	public static <T> DelimitedEntity<T> scan(Class<T> clazz, Configuration conf, boolean replace){
		DelimitedEntity<T> delimitedEntity = (DelimitedEntity<T>) entityCacheMap.get(clazz);
		
		if (delimitedEntity == null || replace){
			ClassHandler clHander = new ClassHandler(clazz).scan();
			if (!clHander.isValid())
				throw new DelimiterException("Annotation [@Delimited] not present on type [" + clazz.getName() + "]");
			
			Delimited delimited = clazz.getAnnotation(Delimited.class);
			switch (delimited.value()) {
			case POSITIONAL:
				delimitedEntity = new PositionalDelimitedEntity<>(clazz, clHander, conf);
				break;
			case TOKEN:
				delimitedEntity = new TokenDelimitedEntity<>(clazz, clHander, conf);
				break;
			}
			
			entityCacheMap.put(clazz, delimitedEntity);
		}
		
		return delimitedEntity;
	}
}
