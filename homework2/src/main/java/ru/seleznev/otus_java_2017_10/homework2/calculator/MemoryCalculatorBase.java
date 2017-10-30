package ru.seleznev.otus_java_2017_10.homework2.calculator;

import static ru.seleznev.otus_java_2017_10.homework2.calculator.ClassMemoryCalculatorException.TAKE_MEMORY_ERROR_CODE;

abstract class MemoryCalculatorBase {
    protected final int DEFAULT_CONTAINER_ELEMENT_NUMBER = defaultContainerSize();

    protected int defaultContainerSize() {
        return 30_000_000;
    }

    protected static CalculatorHelper calculatorHelper;
    protected abstract void initHelper(final int containerSize);

    public Tuple<Long, Object> calc() {
        return calc(DEFAULT_CONTAINER_ELEMENT_NUMBER);
    }

    public Tuple<Long, Object> calc(final int dataElementSize) {
        run(dataElementSize);
        // Put container to returned value. It protects from GC.
        return new Tuple<>(
                calculatorHelper.calcMemoryOnOneInstance(),
                calculatorHelper.getContainer()
        );
    }

    public Tuple<Long, Object> calcContainerSize(final int dataElementSize) {
        run(dataElementSize);
        // Put container to returned value. It protects from GC.
        return new Tuple<>(
                calculatorHelper.calcMemoryOnContainer(),
                calculatorHelper.getContainer()
        );
    }

    private void run(final int dataElementSize) {
        initHelper(dataElementSize);
        calculatorHelper.fixedMemoryOnStart();
        calculatorHelper.createData();
        calculatorHelper.fixedMemoryOnEnd();
    }

    protected abstract class CalculatorHelper {
        protected final int dataElementSize;
        protected Object container; // array of primitive type or array of objects

        private long memoryOnStart;
        private long memoryOnEnd;

        protected CalculatorHelper(final int dataElementSize) {
            this.dataElementSize = dataElementSize;
        }

        public void fixedMemoryOnStart() {
            System.gc();
            this.memoryOnStart = getUsedMemory();
        }

        public void fixedMemoryOnEnd() {
            System.gc();
            this.memoryOnEnd = getUsedMemory();
        }

        private long getUsedMemory(){
            return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        }

        public long calcMemoryOnOneInstance() {
            final double memoryOnInstance = ((double)calcMemoryOnContainer()) / ((double)dataElementSize);
            final long res = Math.round(memoryOnInstance);
            printResult(res);
            return res;
        }

        private void printResult(long res) {
            if (getUsedClassName().isEmpty()) {
                System.out.println("Memory averaged value on instance: " + res);
                return;
            }
            System.out.println("Memory averaged value on instance " + getUsedClassName() + ": " + res);
        }

        public long calcMemoryOnContainer() {
            if (memoryOnEnd < memoryOnStart) {
                throw new ClassMemoryCalculatorException("Unexpected result: reducing the memory after the creation of the data", TAKE_MEMORY_ERROR_CODE);
            }
            return memoryOnEnd - memoryOnStart;
        }

        public Object getContainer() {
            return container;
        }

        protected abstract void createData();
        protected abstract String getUsedClassName();
    }
}
