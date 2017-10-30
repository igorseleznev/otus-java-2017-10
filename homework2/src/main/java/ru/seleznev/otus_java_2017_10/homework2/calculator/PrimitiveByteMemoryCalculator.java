package ru.seleznev.otus_java_2017_10.homework2.calculator;

class PrimitiveByteMemoryCalculator extends MemoryCalculatorBase {
    @Override
    protected int defaultContainerSize() {
        return 10 * super.defaultContainerSize();
    }

    @Override
    protected void initHelper(final int dataElementSize) {
        calculatorHelper = new CalculatorHelper(dataElementSize) {
            @Override
            public void createData() {
                this.container = new byte[dataElementSize];
            }

            @Override
            protected String getUsedClassName() {
                return "byte";
            }
        };
    }
}
