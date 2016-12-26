package br.com.rhm.dlimiter.scan;

import br.com.rhm.dlimiter.annotation.DelimitationType;
import br.com.rhm.dlimiter.reflection.ClassHandler;

public interface DelimitedEntityHandler<T> extends DelimitedEntity<T> {

	ClassHandler getClassHandler();
	
	DelimitationType getDelimitationType();
	
	Configuration getConfiguration();
}
