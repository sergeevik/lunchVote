package lunchVote.repository;

import lunchVote.model.Restaurant;
import lunchVote.model.Vote;

import java.time.LocalDate;
import java.util.Map;

public interface VoiteRepository {
    Vote getByUserIdAndDate(int userId, LocalDate date);
    Vote save(Vote vote, int userId);
    Map<Restaurant, Integer> getRestaurantVotesFromDate(LocalDate date);
}
