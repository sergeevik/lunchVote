package lunchVote.testData;

import lunchVote.model.Lunch;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static lunchVote.testData.RestaurantData.*;

public class LunchData {
    public static final Lunch VOPER = new Lunch(100006, LocalDate.now(), "Voper", 12000, BURGER_KING);
    public static final Lunch BIG_MACK = new Lunch(100007, LocalDate.now(), "Big mack", 8000, MC_DONALD);
    public static final Lunch CHIKEN = new Lunch(100008, LocalDate.now(), "Chiken", 10000, KFC);

    public static List<Lunch> allLunch(){
        return Arrays.asList(VOPER, BIG_MACK, CHIKEN);
    }
}
