package ru.seleznev.otus_java_2017_10.homework2;

import static org.junit.Assert.*;

public class ClassMemoryCalculatorTest {

    @org.junit.Test
    public void testCalcSize() {
        // work only on java x64 (on x86 - calculated size is 32)
        assertEquals(40, ClassMemoryCalculator.size(String.class));

//        assertEquals(1, ClassMemoryCalculator.size(byte.class));
//        assertEquals(1, ClassMemoryCalculator.size(boolean.class));
//        assertEquals(2, ClassMemoryCalculator.size(char.class));
//        assertEquals(2, ClassMemoryCalculator.size(short.class));
//        assertEquals(4, ClassMemoryCalculator.size(int.class));
//        assertEquals(4, ClassMemoryCalculator.size(float.class));
//        assertEquals(8, ClassMemoryCalculator.size(long.class));
//        assertEquals(8, ClassMemoryCalculator.size(double.class));

//        assertEquals(12, ClassMemoryCalculator.size(Object[].class));
//        assertEquals(12, ClassMemoryCalculator.size(byte[].class));

//        assertEquals(8, ClassMemoryCalculator.size(Object.class));
    }

}