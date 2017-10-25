package ru.seleznev.otus_java_2017_10.homework2;


import java.math.BigInteger;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculatorException.CREATE_CLASS_INSTANCE_ERROR_CODE;
import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculatorException.TAKE_MEMORY_ERROR_CODE;
import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculatorException.UNSUPPORTED_OPERATION_ERROR_CODE;
import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculatorUtil.getUsedMemory;

public final class ClassMemoryCalculator {

    private static final Map<Class, MemoryCalculatorBase> calculatorMap =
            ImmutableMap.<Class, MemoryCalculatorBase>builder()
                    .put(boolean.class, new PrimitiveBooleanMemoryCalculator())
                    .put(byte.class, new PrimitiveByteMemoryCalculator())
                    .put(short.class, new PrimitiveShortMemoryCalculator())
                    .put(char.class, new PrimitiveCharMemoryCalculator())
                    .put(int.class, new PrimitiveIntMemoryCalculator())
                    .put(float.class, new PrimitiveFloatMemoryCalculator())
                    .put(long.class, new PrimitiveLongMemoryCalculator())
                    .put(double.class, new PrimitiveDoubleMemoryCalculator())
                    .put(boolean[].class, new PrimitiveBooleanArrayMemoryCalculator())
                    .put(byte[].class, new PrimitiveByteArrayMemoryCalculator())
                    .put(short[].class, new PrimitiveShortArrayMemoryCalculator())
                    .put(char[].class, new PrimitiveCharArrayMemoryCalculator())
                    .put(int[].class, new PrimitiveIntArrayMemoryCalculator())
                    .put(float[].class, new PrimitiveFloatArrayMemoryCalculator())
                    .put(long[].class, new PrimitiveLongArrayMemoryCalculator())
                    .put(double[].class, new PrimitiveDoubleArrayMemoryCalculator())
                    .put(Object.class, new ObjectMemoryCalculator())
                    .put(Object[].class, new ObjectArrayMemoryCalculator())
                    .build();


    public static final long size(final Class clazz) {
        return calculatorMap.getOrDefault(
                clazz,
                clazz.isArray() ? new ObjectArrayMemoryCalculator(clazz) : new ObjectMemoryCalculator(clazz)
        ).calc();
    }

    public static long containerSize(final Class clazz, final int elementNumber) {
        return calculatorMap.getOrDefault(
                clazz,
                clazz.isArray() ? new ObjectArrayMemoryCalculator(clazz) : new ObjectMemoryCalculator(clazz)
        ).calcContainerSize(elementNumber);
    }
}

abstract class MemoryCalculatorBase {
    public long calc() {
        throw new ClassMemoryCalculatorException("Unsupported operation", UNSUPPORTED_OPERATION_ERROR_CODE);
    }

    public long calcContainerSize(final int elementNumber) {
        throw new ClassMemoryCalculatorException("Unsupported operation", UNSUPPORTED_OPERATION_ERROR_CODE);
    }

    abstract protected class CalculatorSizer {
        public static final int DEFAULT_CONTAINER_ELEMENT_NUMBER = 1_000_000;

        private long memoryOnStart;
        private long memoryOnEnd;

        public void fixedMemoryOnStart() {
            this.memoryOnStart = getUsedMemory();
        }

        public void fixedMemoryOnEnd() {
            this.memoryOnEnd = getUsedMemory();
        }

        public long calcMemoryOnOneInstance() {
            final double sizeOnOneInstance = calcMemoryOnContainer()*1.0 / DEFAULT_CONTAINER_ELEMENT_NUMBER;
            return Math.round(sizeOnOneInstance);
        }

        public long calcMemoryOnContainer() {
            if (memoryOnEnd < memoryOnStart) {
                throw new ClassMemoryCalculatorException("Unexpected result", TAKE_MEMORY_ERROR_CODE);
            }
            final long containerSize = (memoryOnEnd - memoryOnStart);
            unlock();
            return containerSize;
        }

        public abstract void unlock();
    }
}

/**
 * For some reason it does not work
 */
class PrimitiveByteMemoryCalculator extends MemoryCalculatorBase {
    private byte[] container = new byte[CalculatorSizer.DEFAULT_CONTAINER_ELEMENT_NUMBER];
    protected CalculatorSizer calculatorSizer = new PrimitiveByteCalculatorSizer(container);

    @Override
    public long calc() {
        calculatorSizer.fixedMemoryOnStart();
        for (int i = 0; i < container.length; i++) {
            container[i] = (byte) (i % 128);
        }
        calculatorSizer.fixedMemoryOnEnd();
        return calculatorSizer.calcMemoryOnOneInstance();
    }

    private class PrimitiveByteCalculatorSizer extends CalculatorSizer {
        private byte[] containerRef;

        PrimitiveByteCalculatorSizer(byte[] container) {
            this.containerRef = container;
        }

        @Override
        public void unlock() {
            containerRef = null;
        }
    }
}

/**
 * For some reason it does not work
 */
class PrimitiveIntMemoryCalculator extends MemoryCalculatorBase {
    private int[] container = new int[CalculatorSizer.DEFAULT_CONTAINER_ELEMENT_NUMBER];
    protected CalculatorSizer calculatorSizer = new PrimitiveIntCalculatorSizer(container);

    @Override
    public long calc() {
        calculatorSizer.fixedMemoryOnStart();
        for (int i = 0; i < container.length; i++) {
            container[i] = i + 1;
        }
        calculatorSizer.fixedMemoryOnEnd();
        return calculatorSizer.calcMemoryOnOneInstance();
    }

    private class PrimitiveIntCalculatorSizer extends CalculatorSizer {
        private int[] containerRef;

        PrimitiveIntCalculatorSizer(int[] container) {
            this.containerRef = container;
        }

        @Override
        public void unlock() {
            containerRef = null;
        }
    }
}

/**
 * It's work on object on x86 java8
 * It's work on String on x64 java8
 */
class ObjectMemoryCalculator extends MemoryCalculatorBase {
    private final Random rnd = new Random(0);
    private Object[] container;
    private CalculatorSizer calculatorSizer;
    private final Class<Object> clazz;

    public ObjectMemoryCalculator(Class clazz) {
        this.clazz = clazz;
    }

    public ObjectMemoryCalculator() {
        this.clazz = Object.class;
    }

    @Override
    public long calc() {
        init(CalculatorSizer.DEFAULT_CONTAINER_ELEMENT_NUMBER);
        calculatorSizer.fixedMemoryOnStart();
        for (int i = 0; i < container.length; i++) {
            container[i] = createObjectInstance();
        }
        calculatorSizer.fixedMemoryOnEnd();
        return calculatorSizer.calcMemoryOnOneInstance();
    }

    @Override
    public long calcContainerSize(final int elementNumber) {
        init(elementNumber);
        calculatorSizer.fixedMemoryOnStart();
        for (int i = 0; i < container.length; i++) {
            container[i] = createObjectInstance();
        }
        calculatorSizer.fixedMemoryOnEnd();
        return calculatorSizer.calcMemoryOnContainer();
    }

    private void init(final int elementNumber) {
        container = new Object[elementNumber];
        calculatorSizer = new ObjectCalculatorSizer(container);
    }

    private Object createObjectInstance() {
        if (String.class.isAssignableFrom(clazz)) {
            return new String(new char[0]);
        }
        if (Integer.class.isAssignableFrom(clazz)) {
            return new Integer(rnd.nextInt());
        }
        if (BigInteger.class.isAssignableFrom(clazz)) {
            return new BigInteger(64, rnd);
        }
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ClassMemoryCalculatorException(e, CREATE_CLASS_INSTANCE_ERROR_CODE);
        }
    }

    private class ObjectCalculatorSizer extends CalculatorSizer {
        private Object[] containerRef;

        ObjectCalculatorSizer(Object[] container) {
            this.containerRef = container;
        }

        @Override
        public void unlock() {
            containerRef = null;
        }
    }
}

// unsupported type calculation
class PrimitiveBooleanMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveShortMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveCharMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveFloatMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveLongMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveDoubleMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveBooleanArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveByteArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveShortArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveCharArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveIntArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveFloatArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveLongArrayMemoryCalculator extends MemoryCalculatorBase {}
class PrimitiveDoubleArrayMemoryCalculator extends MemoryCalculatorBase {}
class ObjectArrayMemoryCalculator extends MemoryCalculatorBase {
    private final Class clazz;

    public ObjectArrayMemoryCalculator(Class clazz) {
        this.clazz = clazz;
    }

    public ObjectArrayMemoryCalculator() {
        this.clazz = Object[].class;
    }
}
