package lunchVote.service;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;

import java.time.LocalDate;

public interface VoteService {
    Vote save(int lunchId, int userId, LocalDate date);
    VoteCounter getDayResult(LocalDate date);
}
