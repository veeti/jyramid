package com.jyramid.config;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Configurator {

    /**
     * Pending routes that have not yet been implemented in the configuration.
     */
    Map<String, Route.Builder> pendingRoutes = new HashMap<>();

    /**
     * Resolved routes with concrete implementations.
     */
    Map<String, Route> resolvedRoutes = new HashMap<>();

    /**
     * Adds a new route to the configuration.
     * @param name Route name
     * @param route Route path
     */
    public void addRoute(String name, String route) {
        if (hasRoute(name))
            throw new RouteConflictException("Route " + name + " already exists.");
        pendingRoutes.put(name, new Route.Builder().setName(name).setRoute(route));
    }

    /**
     * Checks if a route with the specified name exists in this configurator.
     * @param name Route name
     * @return Route exists
     */
    public boolean hasRoute(String name) {
        return pendingRoutes.containsKey(name) || resolvedRoutes.containsKey(name);
    }

    /**
     * Gets pending routes that have not yet been implemented in this configuration.
     * @return Pending routes
     */
    public Map<String, Route.Builder> getPendingRoutes() {
        return Collections.unmodifiableMap(pendingRoutes);
    }

    /**
     * Gets resolved routes.
     * @return Resolved routes
     */
    public Map<String, Route> getResolvedRoutes() {
        return Collections.unmodifiableMap(resolvedRoutes);
    }

    /**
     * Adds the specified object to this configuration's implementation.
     * @param controller Controller
     */
    public void implement(Object controller) {
        Scanner scan = new Scanner(controller);
        Set<String> pendingRemove = new HashSet<>();

        // Resolve routes from this controller
        for (Map.Entry<String, Route.Builder> unresolvedEntry : pendingRoutes.entrySet()) {
            String routeName = unresolvedEntry.getKey();
            Route.Builder builder = unresolvedEntry.getValue();

            Method candidate = scan.findRoute(routeName);
            if (candidate != null) {
                Route builtRoute = builder.setController(controller)
                        .setCallable(candidate)
                        .build();
                resolvedRoutes.put(routeName, builtRoute);
                pendingRemove.add(routeName);
            }
        }

        // Can't remove while looping above. TODO: Cleaner way to do this?
        for (String name : pendingRemove) {
            pendingRoutes.remove(name);
        }
    }

    public Application build() {
        if (pendingRoutes.size() > 0) {
            StringBuilder err = new StringBuilder();
            err.append("Configuration has " + pendingRoutes.size() + " unresolved routes:\n");
            for (Map.Entry<String, Route.Builder> entry : pendingRoutes.entrySet()) {
                err.append("- " + entry.getKey() + "\n");
            }
            throw new PendingRoutesException(err.toString());
        }
        return new Application(resolvedRoutes);
    }

}
