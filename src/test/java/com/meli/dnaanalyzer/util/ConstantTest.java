package com.meli.dnaanalyzer.util;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstantTest {

    @Test
    public void testPrivateConstructor() throws Exception {
        Constructor constructor = Constant.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");

        constructor.setAccessible(true);
        Assert.assertThrows(InvocationTargetException.class, () -> constructor.newInstance());
    }

}