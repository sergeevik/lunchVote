package lunchVote.repository;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository {
    Vote getByUserIdAndDate(int userId, LocalDate date);
    Vote save(int lunchId, int userId, LocalDate date);
    List<VoteCounter> getLunchVotesOnDate(LocalDate date);
}
