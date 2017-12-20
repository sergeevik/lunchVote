package lunchVote.repository.testData;

import lunchVote.model.Lunch;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static lunchVote.repository.testData.RestaurantData.*;

public class LunchData {
    public static final Lunch LUNCH1 = new Lunch(1, LocalDate.now(), "buter", 10000, RESTAURANT1);
    public static final Lunch LUNCH2 = new Lunch(2, LocalDate.now(), "file", 20000, RESTAURANT2);
    public static final Lunch LUNCH3 = new Lunch(3, LocalDate.now(), "meat", 30000, RESTAURANT3);

    public static List<Lunch> allLunch(){
        return Arrays.asList(LUNCH1, LUNCH2, LUNCH3);
    }
}
