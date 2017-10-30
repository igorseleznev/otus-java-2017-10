package ru.seleznev.otus_java_2017_10.homework2.calculator;


import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.ImmutableMap;

public final class MemoryCalculatorUtil {
    private static final Map<Class, MemoryCalculatorBase> mapPrimitiveTypeCalculator = ImmutableMap.<Class, MemoryCalculatorBase>builder()
            .put(boolean.class, new PrimitiveBooleanMemoryCalculator())
            .put(byte.class, new PrimitiveByteMemoryCalculator())
            .put(char.class, new PrimitiveCharMemoryCalculator())
            .put(int.class, new PrimitiveIntMemoryCalculator())
            .put(short.class, new PrimitiveShortMemoryCalculator())
            .put(float.class, new PrimitiveFloatMemoryCalculator())
            .put(long.class, new PrimitiveLongMemoryCalculator())
            .put(double.class, new PrimitiveDoubleMemoryCalculator())
            .build();


    public static final <T> long sizeEmpirically(final Supplier<T> supplier, final int dataElementSize) {
        return new ObjectMemoryCalculator(supplier).calc(dataElementSize).getObj1();
    }

    public static final long sizeEmpirically(final Class clazz) {
        return getCalculatorBy(clazz).calc().getObj1();
    }

    public static <T> long containerSize(final Supplier<T> supplier, final int dataElementSize) {
        return new ObjectMemoryCalculator(supplier).calcContainerSize(dataElementSize).getObj1();
    }

    public static long containerSize(final Class clazz, final int dataElementSize) {
        return getCalculatorBy(clazz).calcContainerSize(dataElementSize).getObj1();
    }

    private static MemoryCalculatorBase getCalculatorBy(final Class clazz) {
        return clazz.isPrimitive()
                ? mapPrimitiveTypeCalculator.get(clazz)
                : new ObjectMemoryCalculator(clazz);
    }
}

