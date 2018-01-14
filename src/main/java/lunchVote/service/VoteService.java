package lunchVote.service;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;

import java.time.LocalDate;
import java.util.List;

public interface VoteService {
    Vote save(int lunchId, int userId, LocalDate date);
    List<VoteCounter> getDayResult(LocalDate date);
}
