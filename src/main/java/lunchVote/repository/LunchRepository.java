package lunchVote.repository;

import lunchVote.model.Lunch;

import java.time.LocalDate;
import java.util.List;

public interface LunchRepository extends MyCrudRepository<Lunch>{

    Lunch save(Lunch object, int restaurantId);

    List<Lunch> getAllForDate(LocalDate date);
}
