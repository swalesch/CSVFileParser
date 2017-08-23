package com.util;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;

public class Util {
    public static String convertListToString(List<Object> list, String splitter) {
        return list.stream()
                .map(Object::toString)
                .collect(joining(splitter));
    }

    public static List<Integer> convertStringToIntList(String line, String splitter) {
        return Arrays.stream(line.split(splitter))
                .map(Integer::parseInt)
                .collect(toList());
    }

    public static List<String> convertStringToStringList(String line, String splitter) {
        line = line + " ";
        return Arrays.stream(line.split(splitter))
                .map(a -> {
                    String b = a.trim();
                    if (b.equals("")) {
                        return "-";
                    }
                    return b;
                })
                .collect(toList());
    }

    public static int getIdOfLowestValue(List<Integer> list) {
        int lowestValue = list.stream()
                .min(Integer::compare)
                .orElseThrow(() -> new RuntimeException("Empty list"));
        return list.indexOf(lowestValue);
    }

    public static int getIdOfHighestValue(List<Integer> list) {
        int lowestValue = list.stream()
                .max(Integer::compare)
                .orElseThrow(() -> new RuntimeException("Empty list"));
        return list.indexOf(lowestValue);
    }
}