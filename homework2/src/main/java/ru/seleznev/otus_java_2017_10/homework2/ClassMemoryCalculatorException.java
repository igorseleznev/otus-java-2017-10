package ru.seleznev.otus_java_2017_10.homework2;


public class ClassMemoryCalculatorException extends RuntimeException {
    public static final int COLLECT_GARBAGE_ERROR_CODE = 1;
    public static final int TAKE_MEMORY_ERROR_CODE = 2;
    public static final int CREATE_CLASS_INSTANCE_ERROR_CODE = 3;
    public static final int UNSUPPORTED_OPERATION_ERROR_CODE = 4;

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
        return "Error code=" + code + ",\n" + super.toString();
    }
}
