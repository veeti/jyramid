package com.jyramid.config;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * A route in the application that matches a specific URL to a controller and method.
 */
public class Route {

    protected String routeName;
    protected String expression;
    protected Object controller;
    protected Method callable;

    protected Route(String routeName, String expression, Object controller, Method callable) {
        this.routeName = routeName;
        this.expression = expression;
        this.controller = controller;
        this.callable = callable;
    }

    public String getName() {
        return routeName;
    }

    public String getRoute() {
        return expression;
    }

    public Object getController() {
        return controller;
    }

    public Method getCallable() {
        return callable;
    }

    /**
     * A builder for creating new route objects.
     */
    public static class Builder {

        String name;
        String route;
        Object controller;
        Method callable;

        /**
         * Constructor.
         */
        public Builder() { }

        /**
         * Sets the route name identifier.
         * @param name Route name
         * @return Builder
         */
        public Builder setName(String name) { this.name = name; return this; }

        /**
         * Sets the route.
         * @param route Route
         * @return Builder
         */
        public Builder setRoute(String route) { this.route = route; return this; }

        /**
         * Sets the resource/controller callable.
         * @param callable Callable
         * @return Builder
         */
        public Builder setCallable(Method callable) { this.callable = callable; return this; }

        /**
         * Sets the controller object.
         * @param controller Controller
         * @return Builder
         */
        public Builder setController(Object controller) { this.controller = controller; return this; }

        /**
         * Builds a route instance.
         * @return Route
         */
        public Route build() {
            if (callable == null)
                throw new IllegalStateException("Callable must not be null.");
            if (controller == null)
                throw new IllegalStateException("Controller must not be null.");
            if (!Arrays.asList(controller.getClass().getMethods()).contains(callable))
                throw new IllegalStateException("Callable must belong to controller.");

            return new Route(name, route, controller, callable);
        }


    }

}
