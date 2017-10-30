package ru.seleznev.otus_java_2017_10.homework2;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static ru.seleznev.otus_java_2017_10.homework2.calculator.MemoryCalculatorUtil.sizeEmpirically;

/**
 * "-ea -Xms0m -Xmx512m" - I used this jvm arguments when started tests.<br>
 *     Results were depended by "-XX:-UseCompressedOops" option.<br>
 * Accuracy is depended by container sizeEmpirically.<br>
 */
public class MemoryCalculatorUtilTest {
    /**
     *      True if "-XX:+UseCompressedOops" or empty jvm args.<br>
     *      False if "-XX:-UseCompressedOops".<br>
     *      By default - true - for successfully mvn building.
     */
    private static final boolean isUsedCompressedOops = true;

    @Test
    public void testCalcMemoryOnPrimitiveTypeOneInstance() {
        assertEquals(1, sizeEmpirically(byte.class));
        assertEquals(1, sizeEmpirically(boolean.class));
        assertEquals(2, sizeEmpirically(char.class));
        assertEquals(2, sizeEmpirically(short.class));
        assertEquals(4, sizeEmpirically(int.class));
        assertEquals(4, sizeEmpirically(float.class));
        assertEquals(8, sizeEmpirically(long.class));
        assertEquals(8, sizeEmpirically(double.class));
    }

    @Test
    public void testCalcMemoryOnObjectOneInstance() throws Exception {
        if (!isUsedCompressedOops) {
            assertEquals(16, sizeEmpirically(Object.class));
            assertEquals(24, sizeEmpirically(Integer.class));
            assertEquals(32, sizeEmpirically(String.class));
            assertEquals(24, sizeEmpirically(Object[].class));
            assertEquals(24, sizeEmpirically(byte[].class));
            assertEquals(24, sizeEmpirically(Boolean.class));
        } else {
            assertEquals(16, sizeEmpirically(Object.class));
            assertEquals(16, sizeEmpirically(Integer.class));
            assertEquals(24, sizeEmpirically(String.class));
            assertEquals(16, sizeEmpirically(Object[].class));
            assertEquals(16, sizeEmpirically(byte[].class));
            assertEquals(16, sizeEmpirically(Boolean.class));
        }
    }

}