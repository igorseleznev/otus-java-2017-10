package ru.seleznev.java_otus_2017_10.homework3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Number> list = new MyArrayList<>();
        System.out.println("Empty list: " + list);
        Collections.addAll(list, 2.5, 10l, 1.0/3, Byte.MAX_VALUE);
        System.out.println("Collections.addAll() - result: " + list);
        List<Number> copyArrayList = new ArrayList(Arrays.asList(new Integer[list.size()]));
        Collections.copy(copyArrayList, list);
        System.out.println("Collections.copy(toArrayList, fromMyArrayList) - result: " + copyArrayList);
        List<Number> copyMyArrayList = new MyArrayList();
        Collections.addAll(copyMyArrayList, null, null, null, null);
        Collections.copy(copyMyArrayList, copyArrayList);
        System.out.println("Collections.copy(toMyArrayList, fromArrayList) - result: " + copyMyArrayList);
        Collections.sort(list, (o1, o2) -> {
            if (o1 == null && o2 == null) {
                return 0; // do nothing, no replace
            }
            if (o1 == null) {
                return 1; // null > any number
            }
            if (o2 == null) {
                return -1; // any number < null
            }
            if (o1.equals(o2)) {
                return 0;
            }
            final double d1 = o1.doubleValue();
            final double d2 = o2.doubleValue();
            return d1 < d2 ? -1 : 1;
        });
        System.out.println("Collections.sort() - result: " + list);
    }
}
