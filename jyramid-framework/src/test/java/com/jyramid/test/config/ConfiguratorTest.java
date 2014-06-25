package com.jyramid.test.config;

import com.jyramid.config.Configurator;
import com.jyramid.config.RouteConflictException;
import com.jyramid.config.PendingRoutesException;
import com.jyramid.controller.Controller;
import com.jyramid.controller.Resource;
import com.jyramid.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConfiguratorTest {

    Configurator cfg;

    @Before
    public void createConfig() {
        cfg = new Configurator();
    }

    /**
     * Route registration.
     */
    @Test
    public void routeAdd() {
        cfg.addRoute("test", "/");
        assertTrue(cfg.getPendingRoutes().containsKey("test"));
    }

    /**
     * Can't register two routes with the same name.
     */
    @Test(expected = RouteConflictException.class)
    public void routeNameConflict() {
        cfg.addRoute("test", "/");
        cfg.addRoute("test", "/test");
    }

    /**
     * hasRoute works.
     */
    @Test
    public void routeHasRoute() {
        assertFalse(cfg.hasRoute("test"));
        cfg.addRoute("test", "/");
        assertTrue(cfg.hasRoute("test"));
    }

    /**
     * Pending routes should be immutable.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void routePendingRoutesImmutable() {
        cfg.getPendingRoutes().put(null, null);
    }

    /**
     * Resolved routes should be immutable.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void routeResolvedRoutesImmutable() {
        cfg.getResolvedRoutes().put(null, null);
    }

    /**
     * Route implementation works.
     */
    @Test
    public void routeImplement() {
        cfg.addRoute("test", "/");
        cfg.implement(new TestController());
        assertFalse(cfg.getPendingRoutes().containsKey("test"));
        assertTrue(cfg.getResolvedRoutes().containsKey("test"));
    }

    /**
     * Can build with all routes implemented.
     */
    @Test
    public void routeImplementAndBuild() {
        cfg.addRoute("test", "/");
        cfg.implement(new TestController());
        cfg.build();
    }

    /**
     * Can't build with pending routes.
     */
    @Test(expected = PendingRoutesException.class)
    public void routeNoImplementationAndBuild() {
        // OK
        cfg.addRoute("test", "/");
        cfg.implement(new TestController());

        // Not OK
        cfg.addRoute("test2", "/two");

        cfg.build();
    }


    @Controller
    public static class TestController {

        @Resource(routeName = "test")
        public void test(HttpRequest req) {
        }

    }

}
