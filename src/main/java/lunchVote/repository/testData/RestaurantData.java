package lunchVote.repository.testData;

import lunchVote.model.Restaurant;

import java.util.Arrays;

public class RestaurantData {

    public final static Restaurant BURGER_KING = new Restaurant(100003, "Burger King", "Square");
    public final static Restaurant MC_DONALD = new Restaurant(100004, "McDonald", "Square");
    public final static Restaurant KFC = new Restaurant(100005, "KFC", "Raduga");

    public static Iterable<Restaurant> getAllData(){
        return Arrays.asList(BURGER_KING, MC_DONALD, KFC);
    }
}
