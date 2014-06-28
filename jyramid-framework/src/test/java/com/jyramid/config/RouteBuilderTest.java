package com.jyramid.config;

import com.jyramid.controller.Controller;
import com.jyramid.controller.Resource;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public final class RouteBuilderTest {

    Object fakeController;
    Method fakeMethod;
    Route.Builder builder;

    @Before
    public void createBuilder() throws NoSuchMethodException {
        fakeController = new FakeController();
        fakeMethod = fakeController.getClass().getMethod("test");
        builder = new Route.Builder()
                .setName("test")
                .setRoute("/")
                .setController(fakeController)
                .setCallable(fakeMethod);
    }

    /**
     * Builder builds properly.
     */
    @Test
    public void builderBuild() {
        builder.build();
    }

    /**
     * Name is built properly.
     */
    @Test
    public void builderName() {
        builder.setName("test");
        assertEquals("test", builder.build().getName());
    }

    /**
     * Route is built properly.
     */
    @Test
    public void builderRoute() {
        builder.setRoute("/");
        assertEquals("/", builder.build().getRoute());
    }

    /**
     * Callable can't be null.
     */
    @Test(expected = IllegalStateException.class)
    public void builderBuildCallableNull() {
        builder.setCallable(null).build();
    }

    /**
     * Controller can't be null.
     */
    @Test(expected = IllegalStateException.class)
    public void builderBuildControllerNull() {
        builder.setController(null).build();
    }

    /**
     * Callable must be from controller.
     */
    @Test(expected = IllegalStateException.class)
    public void builderBuildControllerMustHaveCallable() {
        builder.setController(new Object()).build();
    }

    @Controller
    private static class FakeController {

        @Resource(routeName = "")
        public void test() { }

    }

}
