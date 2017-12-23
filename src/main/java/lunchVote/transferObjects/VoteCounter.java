package lunchVote.transferObjects;

import lunchVote.model.Lunch;

public class VoteCounter {
    private final Lunch lunch;
    private final long countVote;

    public VoteCounter(Lunch lunch, long countVote) {
        this.lunch = lunch;
        this.countVote = countVote;
    }
}
