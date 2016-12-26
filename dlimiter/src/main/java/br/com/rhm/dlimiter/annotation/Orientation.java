package br.com.rhm.dlimiter.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RUNTIME)
public @interface Orientation {

	OrientationPadding padding() default OrientationPadding.RIGHT;
	String fill() default " ";
}
