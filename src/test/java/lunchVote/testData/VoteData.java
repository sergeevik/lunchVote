package lunchVote.testData;

import lunchVote.model.Vote;

import java.time.LocalDate;
import java.util.Arrays;

import static lunchVote.testData.LunchData.*;
import static lunchVote.testData.UserData.*;


/*
  (100000, 100006),
  (100001, 100006),
  (100002, 100008);
 */
public class VoteData {
    public static final Vote ADMIN_VOTE = new Vote(100009, ADMIN, VOPER, LocalDate.now());
    public static final Vote JURA_VOTE = new Vote(100010, JURA, VOPER, LocalDate.now());
    public static final Vote USER_VOTE = new Vote(100011, USER, CHIKEN, LocalDate.now());

    public static Iterable<Vote> getAll(){
        return Arrays.asList(ADMIN_VOTE, JURA_VOTE, USER_VOTE);
    }
}
