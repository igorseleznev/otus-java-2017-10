package ru.seleznev.otus_java_2017_10.homework2;

import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculatorException.COLLECT_GARBAGE_ERROR_CODE;

public class ClassMemoryCalculatorUtil {
    private static final long fSLEEP_INTERVAL = 300;

    public static long getUsedMemory(){
        touchGarbageCollector(2);
        long totalMemory = Runtime.getRuntime().totalMemory();
        touchGarbageCollector(2);
        long freeMemory = Runtime.getRuntime().freeMemory();
        return (totalMemory - freeMemory);
    }

    private static void touchGarbageCollector(final int touchCount) {
        for (int i = 0; i < touchCount; i++) {
            callGarbageCollectorAndSleep();
        }
    }

    private static void callGarbageCollectorAndSleep() {
        try {
            System.gc();
            Thread.currentThread().sleep(fSLEEP_INTERVAL);
            System.runFinalization();
            Thread.currentThread().sleep(fSLEEP_INTERVAL);
        } catch (InterruptedException ex){
            throw new ClassMemoryCalculatorException(ex, COLLECT_GARBAGE_ERROR_CODE);
        }
    }

}
