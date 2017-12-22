package lunchVote.repository;

import lunchVote.model.Lunch;
import lunchVote.model.Vote;

import java.time.LocalDate;
import java.util.Map;

public interface VoteRepository {
    Vote getByUserIdAndDate(int userId, LocalDate date);
    Vote save(int lunchId, int userId);
    Map<Lunch, Integer> getLunchVotesOnDate(LocalDate date);
}
