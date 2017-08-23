package com.util;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class UtilTest {
    @Test
    public void findsSmallestIntegerPosition() {
        List<Integer> input = asList(5, 4, 2, 42, 12);
        int position = Util.getIdOfLowestValue(input);
        assertThat(position).isEqualTo(2);
    }

    @Test
    public void findsHighestIntegerPosition() {
        List<Integer> input = asList(5, 4, 2, 42, 12);
        int position = Util.getIdOfHighestValue(input);
        assertThat(position).isEqualTo(3);
    }

    @Test
    public void splittsNumberSringToList() {
        String input = "0:8:15";
        List<Integer> result = Util.convertStringToIntList(input, ":");
        assertThat(result).containsExactlyElementsOf(asList(0, 8, 15));
    }
}
