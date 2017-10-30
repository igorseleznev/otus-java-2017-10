package ru.seleznev.otus_java_2017_10.homework2;

/**
 * -XX:-UseCompressedOops -Xms0m -Xmx512m
 */
public class Main {
    public static void main(String[] args) {
        try {
            MainConsoleTalkingInterface.mainWrapper(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println(
//                    "BigInteger size: " + MemoryCalculatorUtil.sizeEmpirically(
//                            () -> BigInteger.probablePrime(64, new Random()),
//                            50_000
//                    )
//            );
//            System.out.println(
//                    "Container size [10_000 elements]: " + MemoryCalculatorUtil.containerSize(
//                            () -> BigInteger.probablePrime(64, new Random()),
//                            10_000
//                    )
//            );
        }
    }
}
