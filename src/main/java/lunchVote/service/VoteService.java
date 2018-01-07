package lunchVote.service;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;

import java.time.LocalDate;

public interface VoteService {
    Vote create(int userId, int lunchId);
    Vote update(int userId, int lunchId);
    VoteCounter getDayResult(LocalDate date);
}
