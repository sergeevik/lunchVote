package lunchVote.service;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface VoteService {
    Vote save(int lunchId, int userId, LocalDateTime date);
    List<VoteCounter> getDayResult(LocalDate date);
}
