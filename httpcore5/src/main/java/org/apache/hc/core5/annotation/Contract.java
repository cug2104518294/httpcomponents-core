package org.apache.hc.core5.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation defines behavioral contract enforced at runtime by instances of annotated classes.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface Contract {

    ThreadingBehavior threading() default ThreadingBehavior.UNSAFE;

}
