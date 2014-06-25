package com.jyramid.config;

/**
 * Thrown when a route name is in conflict with the existing configuration.
 */
public class RouteConflictException extends RuntimeException {

    public RouteConflictException(String s) {
        super(s);
    }

}
