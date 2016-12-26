package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

public interface Injector {

	public void inject(Object target, Map<Field, Class<?>> dependencyMap, MemberHandler memberHandler) throws Exception;
	public void inject(Object target, Object value, Field field) throws Exception ;
	public void inject(Object target, Object value, Method setter) throws Exception ;
}
