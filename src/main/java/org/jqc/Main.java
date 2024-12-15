package org.jqc;

import org.jqc.example.annotatedInterface;

import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException {
        annotatedInterface interface1 = new annotatedInterface();
        Class<?> ann = annotatedInterface.class;

        Method run = ann.getDeclaredMethod("run");
        run.toGenericString();
    }
}