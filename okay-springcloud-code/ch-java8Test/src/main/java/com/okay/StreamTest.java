package com.okay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by OKali on 2019/3/16.
 */
public class StreamTest {

    public static void main(String[] args) {
        testForEach();
        System.out.println("-------");
        flatMapTest();
        System.out.println("-------");
        intStreamTest();
        System.out.println("-------");
        mapToInt();
    }

    public static void mapToInt() {
        Stream<int[]> stream = IntStream.rangeClosed(0, 100).boxed().flatMap(a ->
                IntStream.rangeClosed(a, 100).filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                        .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)}));
        stream.limit(5).forEach(t ->
                System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
    }

    // range,rangeClosed
    public static void intStreamTest() {
        IntStream intStream = IntStream.rangeClosed(1, 100).filter(s -> s % 2 == 0);
        System.out.println(intStream.count());
    }

    public static void flatMapTest() {
        String[] arrayOfWords = {"Goodbye", "World"};
        Stream<String> stream = Arrays.stream(arrayOfWords);
        stream.forEach(System.out::println);
        System.out.println("-------");
        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
        List<Integer> list = words.stream().map(s -> s.length()).distinct().collect(Collectors.toList());
        list.stream().forEach(c -> System.out.println(c));
        System.out.println("-------");

        List<String> list1 = words.stream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());
        list1.stream().forEach(c -> System.out.println(c));
    }

    public static void testForEach() {
        List<String> arr = new ArrayList<String>();

        arr.add("abc");
        arr.add("efg");
        arr.add("okay");

        arr.stream().forEach(a -> System.out.println(a));
    }
}
