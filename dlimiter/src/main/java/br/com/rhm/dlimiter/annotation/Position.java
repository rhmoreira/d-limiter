package br.com.rhm.dlimiter.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RUNTIME)
public @interface Position {

	int start();
	int end();
	boolean trim() default true;
	Orientation orientation() default @Orientation;
}
