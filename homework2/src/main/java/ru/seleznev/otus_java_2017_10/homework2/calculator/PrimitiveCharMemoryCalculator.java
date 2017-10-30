package ru.seleznev.otus_java_2017_10.homework2.calculator;

class PrimitiveCharMemoryCalculator extends MemoryCalculatorBase {
    @Override
    protected void initHelper(final int dataElementSize) {
        calculatorHelper = new CalculatorHelper(dataElementSize) {
            @Override
            public void createData() {
                this.container = new char[dataElementSize];
            }

            @Override
            protected String getUsedClassName() {
                return "char";
            }
        };
    }
}
