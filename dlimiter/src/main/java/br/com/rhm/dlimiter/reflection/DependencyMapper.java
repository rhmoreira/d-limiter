package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.util.Map;

public interface DependencyMapper {

	Map<Field, Class<?>> getDependencies();

	boolean isDependency(Class<?> modelClass);
}
