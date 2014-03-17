package org.fenixedu.bennu.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ComposedAnnotation {
    String[] basePackages() default {};

    String[] bundles() default {};
}
