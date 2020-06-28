package com.docker.commonapi.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface PagerPrivoderKeys {

    String routerName() default "";

    String[] providerKeysArr() ;

    Class[] providerObj();

}
