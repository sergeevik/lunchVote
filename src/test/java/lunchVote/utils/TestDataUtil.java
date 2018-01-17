package lunchVote.utils;

import java.util.ArrayList;
import java.util.List;

public class TestDataUtil {

    public static <T> List<T> toList(Iterable<T> collect){
        List<T> target = new ArrayList<>();
        collect.iterator().forEachRemaining(target::add);
        return target;
    }
}
