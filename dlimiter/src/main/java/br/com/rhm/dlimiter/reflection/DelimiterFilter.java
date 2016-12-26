package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import br.com.rhm.dlimiter.DelimiterException;
import br.com.rhm.dlimiter.annotation.Delimited;
import br.com.rhm.dlimiter.annotation.Index;
import br.com.rhm.dlimiter.annotation.Position;

class DelimiterFilter implements ClassMemberFilter{

	public boolean accept(Class<?> clazz){
		return clazz.isAnnotationPresent(Delimited.class);
	}
	public boolean accept(Field field){
		if (field.isAnnotationPresent(Index.class) && field.isAnnotationPresent(Position.class))
			throw new DelimiterException("The field [" + field.getName() + "] can only be annotated with either @Index or @Position annotation");
		
		boolean isNotStatic = !Modifier.isStatic(field.getModifiers());
		boolean isNotTransient = !Modifier.isTransient(field.getModifiers());
		boolean isFieldDelimited = field.isAnnotationPresent(Index.class) || field.isAnnotationPresent(Position.class);
		boolean isDelimitedType = accept(field.getType()) ? isFieldDelimited : false;

		return (isNotStatic && isNotTransient) && (isFieldDelimited || isDelimitedType);
	}
	public boolean accept(Method method){
		return !Modifier.isStatic(method.getModifiers());
	}
}
