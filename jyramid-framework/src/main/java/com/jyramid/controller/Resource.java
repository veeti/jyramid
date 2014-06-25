package com.jyramid.controller;

import com.jyramid.http.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Resource {

    /**
     * The route for this resource.
     */
    String routeName();

    /**
     * The methods accepted by this resource.
     */
    HttpMethod[] methods = new HttpMethod[] { HttpMethod.GET, HttpMethod.POST,
            HttpMethod.DELETE, HttpMethod.PUT, HttpMethod.OPTIONS,
            HttpMethod.HEAD, HttpMethod.TRACE };

}
