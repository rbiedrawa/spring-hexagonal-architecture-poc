package com.rbiedrawa.hexagonal.app.business.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

/**
 * Example how we can remove framework specific stuff from 'business' package and have only one component that depends on Spring framework.
 * Another way is to use @Configuration classes and manually create configuration for 'business' package.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface UseCase {
	/**
	 * Alias for {@link Component#value}.
	 */
	@AliasFor(annotation = Component.class)
	String value() default "";
}
