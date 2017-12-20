package lunchVote.repository.testData;

import lunchVote.model.Restaurant;

import java.util.Arrays;

public class RestaurantData {

    public final static Restaurant RESTAURANT1 = new Restaurant(1, "Big King", "Kremel first door");
    public final static Restaurant RESTAURANT2 = new Restaurant(2, "Кофе Хаус", "Дом с окном");
    public final static Restaurant RESTAURANT3 = new Restaurant(3, "Taco", "In every house");

    public static Iterable<Restaurant> getAllData(){
        return Arrays.asList(RESTAURANT1, RESTAURANT2, RESTAURANT3);
    }
}
