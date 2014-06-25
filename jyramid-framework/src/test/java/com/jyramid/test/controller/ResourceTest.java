package com.jyramid.test.controller;

import com.jyramid.controller.Resource;
import com.jyramid.http.HttpMethod;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public final class ResourceTest {

    private static class TestClass {
        @Resource(routeName = "")
        public void testDefaultDecorator() {
        }
    }

    private Resource getAnnotationFor(String methodName) throws NoSuchMethodException {
        return TestClass.class.getMethod(methodName).getAnnotation(Resource.class);
    }

    /**
     * Ensure that the empty resource annotation accepts all HTTP methods.
     * @throws NoSuchMethodException
     */
    @Test
    public void defaultMethodsAll() throws NoSuchMethodException {
        List<HttpMethod> methods = Arrays.asList(getAnnotationFor("testDefaultDecorator").methods);
        Assert.assertTrue(methods.contains(HttpMethod.GET));
        Assert.assertTrue(methods.contains(HttpMethod.POST));
        Assert.assertTrue(methods.contains(HttpMethod.PUT));
        Assert.assertTrue(methods.contains(HttpMethod.DELETE));
        Assert.assertTrue(methods.contains(HttpMethod.OPTIONS));
        Assert.assertTrue(methods.contains(HttpMethod.HEAD));
        Assert.assertTrue(methods.contains(HttpMethod.TRACE));
    }

}
