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

    public static final Vote ADMIN_VOTE = new Vote(100009, ADMIN.getId(), VOPER.getId(), LocalDate.now());
    public static final Vote JURA_VOTE = new Vote(100010, JURA.getId(), VOPER.getId(), LocalDate.now());
    public static final Vote USER_VOTE = new Vote(100011, USER.getId(), CHIKEN.getId(), LocalDate.now());
    public static final Vote SAVE_VOTE_COUNT_QUERY = new Vote(null, USER.getId(), CHIKEN.getId(), LocalDate.now().plusDays(3));

    public static Iterable<Vote> getAll(){
        return Arrays.asList(ADMIN_VOTE, JURA_VOTE, USER_VOTE);
    }
}
