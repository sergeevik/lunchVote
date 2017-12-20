package lunchVote.model;

import java.time.LocalDate;

public class Lunch extends AbstractBaseEntity{
    private LocalDate date;
    private String Description;
    private Integer price;
    private Restaurant restaurant;
}
