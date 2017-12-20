package lunchVote;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomAssert {

    public static <T> void assertMatch(T actual, T expected){
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }
    public static <T> void assertMatch(Iterable<T> actual, T... expected){
        assertMatch(actual, Arrays.asList(expected));
    }
    public static <T> void assertMatch(Iterable<T> actual, Iterable<T> expected){
        assertThat(actual).containsAll(expected);
    }
}
