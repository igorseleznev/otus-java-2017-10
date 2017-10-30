package ru.seleznev.otus_java_2017_10.homework2.calculator;

import com.google.common.collect.ImmutableMap;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import static ru.seleznev.otus_java_2017_10.homework2.calculator.ClassMemoryCalculatorException.CREATE_CLASS_INSTANCE_ERROR_CODE;

class ObjectMemoryCalculator extends MemoryCalculatorBase {
    private static final Map<Class, Object> mapPrimitiveTypeValue = ImmutableMap.<Class, Object>builder()
            .put(boolean.class, Boolean.TRUE)
            .put(byte.class, (byte) new Random().nextInt() % 128)
            .put(char.class, (char) new Random().nextInt() % 256)
            .put(short.class, (short) new Random().nextInt() % 256)
            .put(int.class, new Random().nextInt())
            .put(float.class, (float) new Random().nextInt())
            .put(long.class, (long) new Random().nextInt())
            .put(double.class, (double) new Random().nextInt())
            .build();
    private boolean isNoHaveEmptyConstructor;

    private final Class<Object> clazz;
    private final Supplier supplier;

    public ObjectMemoryCalculator(final Class clazz) {
        this.clazz = clazz;
        this.supplier = null;
    }

    public <T> ObjectMemoryCalculator(Supplier<T> supplier) {
        this.clazz = null;
        this.supplier = supplier;
    }

    @Override
    protected int defaultContainerSize() {
        return 5_000_000;
    }

    @Override
    protected void initHelper(final int dataElementSize) {
        calculatorHelper = new ObjectCalculatorHelper(dataElementSize);
    }

    private Object createObjectInstance(final Class clazz) {
        if (supplier != null) {
            return supplier.get();
        }
        if (clazz.isPrimitive()) {
            return mapPrimitiveTypeValue.get(clazz);
        }
        if (clazz.isArray()) {
            return arrayValue(clazz);
        }

        if (!isNoHaveEmptyConstructor) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                isNoHaveEmptyConstructor = true;
            }
        }

        try {
            Constructor[] constructors = clazz.getConstructors();
            for (int i = 0; i < constructors.length; i++) {
                final Constructor constructor = constructors[i];
                final Object[] args = createArgs(constructor.getParameterTypes());
                return constructor.newInstance(args);
            }
            return clazz.newInstance();
        } catch (Exception e) {
            throw new ClassMemoryCalculatorException(e, CREATE_CLASS_INSTANCE_ERROR_CODE);
        }
    }

    private Object arrayValue(Class clazz) {
        final Map<Class, Object> mapArrayTypeValue = ImmutableMap.<Class, Object>builder()
                .put(boolean[].class, new boolean[0])
                .put(byte[].class, new byte[0])
                .put(char[].class, new char[0])
                .put(short[].class, new short[0])
                .put(int[].class, new int[0])
                .put(float[].class, new float[0])
                .put(long[].class, new long[0])
                .put(double[].class, new double[0])
                .put(Object[].class, new Object[0])
                .build();
        return mapArrayTypeValue.get(clazz);
    }

    private Object[] createArgs(final Class[] parameterTypes) {
        if (parameterTypes.length == 0) {
            return new Object[0];
        }
        Object[] res = new Object[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            res[i] = createObjectInstance(parameterTypes[i]);
        }
        return res;
    }

    private class ObjectCalculatorHelper extends CalculatorHelper {

        public ObjectCalculatorHelper(final int dataElementSize) {
            super(dataElementSize);
            this.container = new Object[dataElementSize];
        }

        @Override
        public void createData() {
            Object[] arr = (Object[]) this.container;
            for (int i = 0; i < arr.length; i++) {
                arr[i] = createObjectInstance(clazz);
            }
        }

        @Override
        protected String getUsedClassName() {
            return clazz == null ? "" : clazz.getSimpleName();
        }
    }
}
