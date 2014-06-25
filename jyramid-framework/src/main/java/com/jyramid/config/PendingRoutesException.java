package com.jyramid.config;

/**
 * Raised when a route has not been implemented in the built configuration.
 */
public class PendingRoutesException extends RuntimeException {

    public PendingRoutesException(String msg) {
        super(msg);
    }

}
