package com.okay;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Created by OKali on 2019/3/10.
 */
public class TestJava8 {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a","b","c","d");
        Iterables.forEach(list,(index, str)-> {
            Optional<String> s1 = Optional.of(str).filter(s -> index < 2).map(ss -> ss.toString());
            System.out.println(s1.orElse("大于等于2") + ":" + index);
        });
    }
}
