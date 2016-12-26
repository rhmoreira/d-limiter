package br.com.rhm.dlimiter.core;

import br.com.rhm.dlimiter.scan.DelimitedEntity;
import br.com.rhm.dlimiter.scan.DelimiterScanner;

public final class Delimiters {
	
	private Delimiters() {
	}
	
	public static <T> Delimiter<T> getInstance(Class<T> clazz){
		DelimitedEntity<T> delimitedEntity = DelimiterScanner.scan(clazz);
		return new DelimiterImpl<>(delimitedEntity);
	}

}
