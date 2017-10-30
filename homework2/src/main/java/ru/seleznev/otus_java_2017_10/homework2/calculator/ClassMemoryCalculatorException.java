package ru.seleznev.otus_java_2017_10.homework2.calculator;


class ClassMemoryCalculatorException extends RuntimeException {
    public static final int TAKE_MEMORY_ERROR_CODE = 1;
    public static final int CREATE_CLASS_INSTANCE_ERROR_CODE = 2;

    private int code = 0;

    public ClassMemoryCalculatorException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ClassMemoryCalculatorException(Throwable t, int code) {
        super(t);
        this.code = code;
    }

    @Override
    public String toString() {
        switch (code) {
            case CREATE_CLASS_INSTANCE_ERROR_CODE:
                return "Class instance cannot created automatically.\nIf you want to check this class, " +
                        "check it via manual interface (via Supplier)";
            default:
                return "Error code=" + code + ",\n" + super.toString();
        }
    }
}
