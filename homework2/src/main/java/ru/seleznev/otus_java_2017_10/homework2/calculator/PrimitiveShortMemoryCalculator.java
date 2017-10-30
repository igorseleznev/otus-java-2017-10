package ru.seleznev.otus_java_2017_10.homework2.calculator;

class PrimitiveShortMemoryCalculator extends MemoryCalculatorBase {
    @Override
    protected void initHelper(final int dataElementSize) {
        calculatorHelper = new CalculatorHelper(dataElementSize) {
            @Override
            public void createData() {
                this.container = new short[dataElementSize];
            }

            @Override
            protected String getUsedClassName() {
                return "short";
            }
        };
    }
}
