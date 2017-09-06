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

    @Test
    public void testConvertStringToStringListWithEscape() {
        String input = "Population,Parent Name,#Events,%Parent,%Grand Parent,%Total,HLA-DR V500-A Mean,CD69 APC-A Mean,CD25 FITC-A Mean,CCR7 PE-CF594-A Mean";
        List<String> result = Util.convertStringToStringListWithEscape(input, ",");

        assertThat(result)
                .containsExactlyElementsOf(asList("Population", "Parent Name", "#Events", "%Parent", "%Grand Parent",
                        "%Total", "HLA-DR V500-A Mean", "CD69 APC-A Mean", "CD25 FITC-A Mean", "CCR7 PE-CF594-A Mean"));

        String input2 = "Record Date,\"Mar 18, 2014 12:38:58 PM\"";
        List<String> result2 = Util.convertStringToStringListWithEscape(input2, ",");
        assertThat(result2).containsExactlyElementsOf(asList("Record Date", "\"Mar 18, 2014 12:38:58 PM\""));

        String input3 = "11,,";
        List<String> result3 = Util.convertStringToStringListWithEscape(input3, ",");
        assertThat(result3).containsExactlyElementsOf(asList("11", "-", "-"));

        String input4 = "a,\"b,c\",,d,\"e\",\"f\",,";
        List<String> result4 = Util.convertStringToStringListWithEscape(input4, ",");
        assertThat(result4).containsExactlyElementsOf(asList("a", "\"b,c\"", "-", "d", "\"e\"", "\"f\"", "-", "-"));
    }
}
