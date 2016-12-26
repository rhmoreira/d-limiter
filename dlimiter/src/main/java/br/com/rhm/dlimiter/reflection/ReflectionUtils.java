package br.com.rhm.dlimiter.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import br.com.rhm.dlimiter.reflection.MemberHandler.MethodHandler;

public class ReflectionUtils {
	
	public static final Object[] EMPTY_ARGS = {};
	public static final Class<?>[] EMPTY_ARGS_TYPE = {};

	public static void setFieldValue(Object target, Object value, Field field) throws Exception{
		setAccessible(field);
		
		field.set(target, value);
	}
	
	public static <T> T getFieldValue(Object target, Field field) throws Exception{
		setAccessible(field);
		
		return (T) field.get(target);
	}
	
	private static void setAccessible(Field field){
		if (!field.isAccessible())
			field.setAccessible(true);
	}
	
	public static Object invokeMethod(Object target, Method method, Object... value) throws Exception{
		return method.invoke(target, value);
	}
	
	public static <T extends Annotation> Annotation getStereotypedAnnotation(Class<T> stereotype, Field field){
		for (Annotation a: field.getAnnotations())
			if (a.annotationType().isAnnotationPresent(stereotype))
				return a;
		
		return null;
	}
	
	public static void copyProperties(Object src, Object dest) throws Exception{
		ClassHandler srcCH = createHandler(src);
		ClassHandler destCH = createHandler(dest);
		
		Map<String, Field> mappedDestFields = destCH.getMappedFields();
		srcCH.getScannedFields()
			 .stream()
			 .filter(f -> mappedDestFields.containsKey(f.getName()))
			 .forEach(f -> {
					 Field destField = mappedDestFields.get(f.getName());
				 try{
					 MethodHandler srcGetter = srcCH.getMemberHandler().getterMethodForField(f);
					 MethodHandler destSetter = destCH.getMemberHandler().setterMethodForField(destField);
					 if (srcGetter != null && destSetter != null)
						 destSetter.invoke(dest, srcGetter.invoke(src));
				 }catch (Exception e) {
					throw new RuntimeException(e);
				}
			 });
	}
	
	private static ClassHandler createHandler(Object obj) throws Exception{
		return new ClassHandler(obj.getClass(), Object.class).scan();
	}
}