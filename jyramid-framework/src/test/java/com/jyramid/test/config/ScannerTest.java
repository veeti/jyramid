package com.jyramid.test.config;

import com.jyramid.config.Scanner;
import com.jyramid.controller.Controller;
import com.jyramid.controller.Resource;
import com.jyramid.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ScannerTest {

    TestController controller;
    Scanner scanner;

    @Before
    public void createScanner() {
        controller = new TestController();
        scanner = new Scanner(controller);
    }

    /**
     * Scanner finds resource method with specified route name.
     * @throws NoSuchMethodException
     */
    @Test
    public void scanForRouteName() throws NoSuchMethodException {
        Method expected = controller.getClass().getMethod("testMethod", HttpRequest.class);
        Method actual = scanner.findRoute("route1");
        assertEquals(expected, actual);
    }

    @Controller
    private static class TestController {

        public TestController() { }

        @Resource(routeName = "route1")
        public void testMethod(HttpRequest request) {
        }

        @Resource(routeName = "route2")
        public void testMethodTwo(HttpRequest request) {
        }

    }

}
