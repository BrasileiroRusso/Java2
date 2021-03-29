package ru.geekbrains.vklimovich.vunit;

import java.lang.reflect.*;
import java.util.*;

public class VUnit {

    private static class TestMethod implements Comparable<TestMethod>{
        Method method;
        int priority;

        TestMethod(Method method, int priority){
            this.method = method;
            this.priority = priority;
        }

        @Override
        public int compareTo(TestMethod o) {
            return Integer.compare(this.priority, o.priority);
        }
    }

    public static <T> void start(Class<T> cl) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Method initMethod = null;
        Method resultMethod = null;
        Method objCreator = null;
        PriorityQueue<TestMethod> testMethods = new PriorityQueue<TestMethod>();
        List<String> incorrectTests = new LinkedList<String>();
        Object testObject;
        boolean success;
        long startTime = 0, finishTime = 0;

        for(Method m: cl.getDeclaredMethods()){
            BeforeSuite beforeSuite = m.getAnnotation(BeforeSuite.class);
            if(beforeSuite != null){
                if(initMethod != null)
                    throw new AnnotationUseException("Only one method can be annotated with @BeforeSuite");
                if(!Modifier.isPublic(m.getModifiers()))
                    throw new AnnotationUseException("Method marked with @BeforeSuite have to be public");
                initMethod = m;
            }

            AfterSuite afterSuite = m.getAnnotation(AfterSuite.class);
            if(afterSuite != null){
                if(resultMethod != null)
                    throw new AnnotationUseException("Only one method can be annotated with @AfterSuite");
                if(!Modifier.isPublic(m.getModifiers()))
                    throw new AnnotationUseException("Method marked with @AfterSuite has to be public");
                resultMethod = m;
            }

            ObjectInit objectInit = m.getAnnotation(ObjectInit.class);
            if(objectInit != null){
                if(objCreator != null)
                    throw new AnnotationUseException("Only one method can be annotated with @ObjectInit");
                if(!m.getReturnType().equals(cl))
                    throw new AnnotationUseException("Illegal return type for the method with @ObjectInit");
                if(!Modifier.isStatic(m.getModifiers()))
                    throw new AnnotationUseException("Method marked with @ObjectInit have to be static");
                objCreator = m;
                objCreator.setAccessible(true);
            }

            Test test = m.getAnnotation(Test.class);
            if(test == null)
                continue;
            int priority = test.value();
            if(priority <= 0)
                priority = Integer.MAX_VALUE;
            if(m.getParameterTypes().length == 0){
                m.setAccessible(true);
                testMethods.offer(new TestMethod(m, priority));
            }
            else
                incorrectTests.add(m.getName());
        }

        try {
            if(objCreator == null && !Modifier.isPublic(cl.getConstructor().getModifiers()))
                throw new IllegalAccessException("The default constructor has to be public");
        } catch (NoSuchMethodException e) {
            throw new IllegalAccessException("The default constructor doesn't exist");
        }

        System.out.println("--- Start tests for the class " + cl.getCanonicalName() + " ---");
        if(objCreator != null)
            testObject = objCreator.invoke(cl);
        else
            testObject = cl.newInstance();

        initMethod.invoke(testObject);
        while(!testMethods.isEmpty()){
            Method m = testMethods.remove().method;
            System.out.println(String.format("--- Test %s started ---", m.getName()));
            try{
                success = true;
                if(m.getReturnType().equals(boolean.class)){
                    startTime = System.nanoTime();
                    success = (Boolean) m.invoke(testObject);
                    finishTime = System.nanoTime();
                }
                else{
                    startTime = System.nanoTime();
                    m.invoke(testObject);
                    finishTime = System.nanoTime();
                }

            } catch(InvocationTargetException e){
                e.printStackTrace();
                success = false;
            }
            if(success)
                System.out.println(String.format("Success (time = %dms)", (finishTime - startTime)/1000000));
            else
                System.out.println("Failed");
        }
        resultMethod.invoke(testObject);
        testObject = null;

        if(incorrectTests.size() > 0){
            System.out.println();
            System.out.println("These methods cannot be tested (because they have parameters):");
            for(String methodName: incorrectTests)
                System.out.println(methodName);
        }

    }

    public static void start(String className) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> cl = Class.forName(className);
        start(cl);
    }
}
