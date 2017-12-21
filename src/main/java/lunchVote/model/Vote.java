package lunchVote.model;

import java.time.LocalDate;

public class Vote extends AbstractBaseEntity{
    private User user;
    private Lunch lunch;
    private LocalDate date;
}
