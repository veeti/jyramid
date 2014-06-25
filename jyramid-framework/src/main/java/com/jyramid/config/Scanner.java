package com.jyramid.config;

import com.jyramid.controller.Resource;

import java.lang.reflect.Method;

/**
 * Scans configuration items from controller classes.
 */
public class Scanner {

    Object target;

    /**
     * Instantiates a scanner for the specified target object.
     * @param target Target
     */
    public Scanner(Object target) {
        this.target = target;
    }

    /**
     * Tries to find a resource method for the specified route name.
     * @param routeName Route name
     * @return Method or null if not found
     */
    public Method findRoute(String routeName) {
        for (Method method : target.getClass().getMethods()) {
            Resource annotation = method.getAnnotation(Resource.class);
            if (annotation != null && annotation.routeName().equals(routeName)) {
                return method;
            }
        }
        return null;
    }

}
