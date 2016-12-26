package br.com.rhm.dlimiter.scan;

import br.com.rhm.dlimiter.annotation.DelimitationType;
import br.com.rhm.dlimiter.annotation.Delimited;
import br.com.rhm.dlimiter.reflection.ClassHandler;

public abstract class AbstractDelimitedEntity<T> implements DelimitedEntityHandler<T>, DelimitedEntity<T> {

	protected Class<T> clazz;
	protected ClassHandler classHandler;
	protected Configuration conf;
	
	public AbstractDelimitedEntity(Class<T> clazz, ClassHandler classHandler, Configuration conf) {
		super();
		this.clazz = clazz;
		this.classHandler = classHandler;
		this.conf = conf;
	}

	@Override
	public Class<T> getEntityClass() {
		return clazz;
	}

	@Override
	public ClassHandler getClassHandler() {
		return classHandler;
	}

	@Override
	public DelimitationType getDelimitationType() {
		Delimited delimited = clazz.getAnnotation(Delimited.class);
		return delimited.value();
	}

	@Override
	public Configuration getConfiguration() {
		return conf;
	}
}
