package com.urise.webapp.concurensy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {
        int[] array = new int[]{5, 8, 8, 8, 1, 2, 3, 3, 2, 3};
        System.out.println(minValue(array));
        try {
            System.out.println(minValue(new int[0]));
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
        List<Integer> integers = Arrays.stream(array).boxed().toList();
        System.out.println(oddOrEven(integers));
        System.out.println(oddOrEven(List.of(1, 2, 3, 4, 4, 5, 5, 6, 6, 32)));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> map = integers.stream().collect(Collectors.partitioningBy(x -> x % 2 != 0));
        return map.get(true).size() % 2 == 0 ? map.get(true) : map.get(false);
    }


    private static int minValue(int[] values) {
        OptionalInt optional = Arrays.stream(values).distinct().sorted()
                .reduce((result, element) -> result * 10 + element);
        if (optional.isPresent()) {
            return optional.getAsInt();
        } else {
            throw new IllegalArgumentException("array is empty");
        }
    }
}
