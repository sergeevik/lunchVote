package lunchVote.util;

import lunchVote.model.Lunch;
import lunchVote.transferObjects.LunchTransfer;

public class LunchConverter {
    public static Lunch fromTo(LunchTransfer to) {
        return new Lunch(to.getId(),
                         to.getDate(),
                         to.getDescription(),
                         PriceConverter.fromDouble(to.getPrice()),
                         null);

    }

    public static LunchTransfer asTo(Lunch lunch) {
        return new LunchTransfer(lunch.getId(),
                lunch.getDescription(),
                PriceConverter.fromInteger(lunch.getPrice()),
                lunch.getDate(),
                lunch.getRestaurant().getId());

    }
}
