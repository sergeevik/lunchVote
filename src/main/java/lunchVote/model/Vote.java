package lunchVote.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity{
    private User user;
    private Restaurant restaurant;
    private LocalDate date;
}
