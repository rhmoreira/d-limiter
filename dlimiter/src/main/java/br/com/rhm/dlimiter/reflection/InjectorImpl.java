package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

class InjectorImpl implements Injector {
	
	@Override
	public void inject(Object target, Object value, Method setter) throws Exception {
		ReflectionUtils.invokeMethod(target, setter, value);
	}
	
	@Override
	public void inject(Object target, Object value, Field field) throws Exception {
		ReflectionUtils.setFieldValue(target, value, field);
	}

	@Override
	public void inject(Object target, Map<Field, Class<?>> dependencyMap, MemberHandler memberHandler) throws Exception {
		Map<Class<?>, Object> instanceCacheL1 = new HashMap<>();
		
		instanceCacheL1.put(target.getClass(), target);
		
		dependencyMap
			.entrySet()
			.stream()
			.forEach(entry -> {
				try{
					Object dependencyInstance = instanceCacheL1.get(entry.getValue());
					if (dependencyInstance == null){
						dependencyInstance = entry.getValue().getDeclaredConstructor(ReflectionUtils.EMPTY_ARGS_TYPE).newInstance(ReflectionUtils.EMPTY_ARGS);
						instanceCacheL1.put(entry.getValue(), dependencyInstance);
					}
					inject(target, dependencyInstance, entry.getKey());
				}catch (Exception e) {
					e.printStackTrace();
				}
			});
	}
}
