package ru.seleznev.java_otus_2017_10.homework3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Number> list = new MyArrayList<>();
        Collections.addAll(list, 2.5, 10l, 1.0/3, Byte.MAX_VALUE);
        System.out.println("Collections.addAll() - result: " + list);
        List<Number> copyList = new ArrayList(Arrays.asList(new Integer[list.size()]));
        Collections.copy(copyList, list);
        System.out.println("Collections.copy() - result: " + copyList);
        Collections.sort(copyList, (o1, o2) -> {
            if (o1.equals(o2)) {
                return 0;
            }
            final double d1 = o1.doubleValue();
            final double d2 = o2.doubleValue();
            return d1 < d2 ? -1 : 1;
        });
        System.out.println("Collections.sort() - result: " + copyList);
    }
}
