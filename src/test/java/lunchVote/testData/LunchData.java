package lunchVote.testData;

import lunchVote.model.Lunch;

import java.time.LocalDate;
import java.util.Arrays;

import static lunchVote.testData.RestaurantData.*;

public class LunchData {
    public static final Lunch VOPER = new Lunch(100006, LocalDate.now(), "Voper", 12000, BURGER_KING);
    public static final Lunch BIG_MACK = new Lunch(100007, LocalDate.now(), "Big mack", 8000, MC_DONALD);
    public static final Lunch CHIKEN = new Lunch(100008, LocalDate.now(), "Chiken", 10000, KFC);
    public static final Lunch SAVE_NEW = new Lunch(null, LocalDate.now(), "Gamburger", 4500, BURGER_KING);

    public static Iterable<Lunch> allLunch(){
        return Arrays.asList(VOPER, BIG_MACK, CHIKEN);
    }
}
