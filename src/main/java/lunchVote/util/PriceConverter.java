package lunchVote.util;

public class PriceConverter {
    public static Integer fromDouble(Double price){
        return new Double(price * 100).intValue();
    }

    public static Double fromInteger(Integer price){
        return price / 100.0;
    }
}
