package com.vitalflow.test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * A lightweight unit testing framework to avoid external dependencies like Maven/Gradle/JUnit jars.
 */
public class SimpleTestRunner {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {}

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Before {}

    public static void runTests(Class<?>... classes) {
        int totalTests = 0;
        int passedTests = 0;
        int failedTests = 0;

        System.out.println("==========================================");
        System.out.println("   VitalFlow Unit Test Suite Execution    ");
        System.out.println("==========================================");

        for (Class<?> testClass : classes) {
            System.out.println("Running " + testClass.getSimpleName() + "...");
            Object instance;
            try {
                instance = testClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                System.err.println("FAILED to instantiate " + testClass.getName());
                e.printStackTrace();
                continue;
            }

            // Find setup method
            Method beforeMethod = null;
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Before.class)) {
                    beforeMethod = m;
                    break;
                }
            }

            // Run tests
            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(Test.class)) {
                    totalTests++;
                    try {
                        if (beforeMethod != null) {
                            beforeMethod.invoke(instance);
                        }
                        m.invoke(instance);
                        System.out.println("  [PASS] " + m.getName());
                        passedTests++;
                    } catch (Exception e) {
                        System.err.println("  [FAIL] " + m.getName());
                        // Unwrap the reflection exception
                        Throwable cause = e.getCause();
                        if (cause != null) {
                            System.err.println("    Reason: " + cause.getMessage());
                            // cause.printStackTrace(); 
                        } else {
                            e.printStackTrace();
                        }
                        failedTests++;
                    }
                }
            }
            System.out.println();
        }

        System.out.println("==========================================");
        System.out.println("SUMMARY:");
        System.out.println("Total:  " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("==========================================");
        
        if (failedTests > 0) System.exit(1);
    }

    // --- Assertion Helpers ---
    
    public static void assertEquals(int expected, int actual) {
        if (expected != actual) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }
}
