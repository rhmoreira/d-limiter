package br.com.rhm.dlimiter.reflection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class DependencyMapperImpl implements DependencyMapper{

	private ClassHandler classHandler;
	private Map<Field, Class<?>> dependencyMap = new HashMap<>();
	private Collection<Class<?>> dependencyClasses;

	public DependencyMapperImpl(ClassHandler classHandler) {
		super();
		this.classHandler = classHandler;
	}
	
	public void mapModelDependencies() {
		Map<Field, Class<?>> dependencyMap = filterDependencyClasses();
		this.dependencyMap = dependencyMap;
		dependencyClasses = dependencyMap.values();
	}
	
	@Override
	public boolean isDependency(Class<?> modelClass) {
		if (modelClass == null)
			return false;
		return dependencyClasses.contains(modelClass);
	}
	
	@Override
	public Map<Field, Class<?>> getDependencies() {
		return dependencyMap;
	}
	
	private Map<Field, Class<?>> filterDependencyClasses(){
		ClassMemberFilter filter = classHandler.getFilter();
		Collection<Field> scannedFields = classHandler.getScannedFields();
		
		Map<Field, Class<?>> dependentClasses = 
				scannedFields.stream()
							 .filter(f -> filter.accept(f.getType()))
							 .collect(
								Collectors.toMap(
									(Function<Field,Field>) f -> f,
									(Function<Field,Class<?>>) f -> f.getType()
								)
							 );
		return dependentClasses;
	}
}
