package com.jyramid.config;

import com.jyramid.http.HttpRequestImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class Application {

    Map<String, Route> routes;
    // Variable routes? You wish.
    Map<String, Route> routesByPath = new HashMap<>();

    protected Application(Map<String, Route> routes) {
        this.routes = routes;

        for (Map.Entry<String, Route> route : routes.entrySet()) {
            routesByPath.put(route.getValue().expression, route.getValue());
        }
    }

    public Object getResultForPath(String path) throws InvocationTargetException, IllegalAccessException {
        // A quick hack to get something that works, pending real routing.
        if (routesByPath.containsKey(path)) {
            Route route = routesByPath.get(path);
            return route.callable.invoke(route.controller, new HttpRequestImpl());
        }
        return null;
    }

}
