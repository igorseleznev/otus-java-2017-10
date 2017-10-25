package ru.seleznev.otus_java_2017_10.homework2;

import com.google.common.collect.ImmutableSortedSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static ru.seleznev.otus_java_2017_10.homework2.ClassMemoryCalculator.containerSize;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger("");
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        logger.info("Hello! This is class memory calculator");
        if (args != null && args.length != 0) {
            final Set<String> uniqueArgs = ImmutableSortedSet.copyOf(Arrays.asList(args));
            for (final String arg : uniqueArgs) {
                run(arg);
            }
        } else {
            logger.info("Please, enter class full name ('-h' - help)");
            String input = scanner.nextLine();
            while ("-h".equals(input) || "--help".equals(input)) {
                logger.info("Help instructions:");
                logger.info("\t\t'1' - 'java.lang.String' hotkey");
                logger.info("\t\t'2' - 'java.lang.Object' hotkey");
                logger.info("\t\t'3' - 'java.lang.Integer' hotkey");
                logger.info("\t\t'4' - 'java.math.BigInteger' hotkey");
                input = scanner.nextLine();
            }
            switch (input) {
                case "1":
                    input = "java.lang.String";
                    break;
                case "2":
                    input = "java.lang.Object";
                    break;
                case "3":
                    input = "java.lang.Integer";
                    break;
                case "4":
                    input = "java.math.BigInteger";
                    break;
            }
            run(input);
        }
    }

    private static void run(final String className) {
        for (final Class clazz : getClass(className)) {
            logger.info("Class '{}' is found. Calculation started", clazz.getCanonicalName());
            logger.info("Class '{}' takes {} bytes", clazz.getCanonicalName(), ClassMemoryCalculator.size(clazz));
            logger.info("");
            logger.info("Enter the count of container elements");
            final String input = scanner.nextLine();
            try {
                final int elementNumber = Integer.parseInt(input);
                logger.info("{}[{}] takes {} bytes",
                        clazz.getSimpleName(),
                        elementNumber,
                        containerSize(clazz, elementNumber)
                );
            } catch (NumberFormatException e) {
                logger.info("Wrong number format '{}'\n", input, e);
            } catch (Exception e) {
                logger.info("Input number '{}'\n", input, e);
            }
        }
    }

    private static List<Class> getClass(final String className) {
        try {
            switch (className) {
                case "boolean":
                    return Arrays.asList(boolean.class);
                case "byte":
                    return Arrays.asList(byte.class);
                case "short":
                    return Arrays.asList(short.class);
                case "char":
                    return Arrays.asList(char.class);
                case "int":
                    return Arrays.asList(int.class);
                case "float":
                    return Arrays.asList(float.class);
                case "long":
                    return Arrays.asList(long.class);
                case "double":
                    return Arrays.asList(double.class);
                default:
                    return Arrays.asList(Class.forName(className));
            }
        } catch (ClassNotFoundException e) {
            logger.info("Class '{}' is not found", className);
            return Arrays.asList();
        }
    }

}
