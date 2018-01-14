package lunchVote.repository;

import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.transferObjects.VoteCounter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.testData.LunchData.CHIKEN;
import static lunchVote.testData.UserData.ADMIN;
import static lunchVote.testData.UserData.JURA;
import static lunchVote.testData.VoteData.JURA_VOTE;
import static lunchVote.testData.VoteData.SAVE_VOTE_COUNT_QUERY;
import static lunchVote.testData.VoteData.USER_VOTE;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteRepositoryTest extends SpringConfigOnTests{

    @Autowired
    private VoteCrud repository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getByUserIdAndDateNull() throws Exception {
        Vote byUserIdAndDate = repository.findByUserAndDate(ADMIN.getId(), LocalDate.now().minusDays(5));
        assertThat(byUserIdAndDate).isNull();
    }

    @Test
    public void getByUserIdAndDate() throws Exception {
        Vote byUserIdAndDate = repository.findByUserAndDate(JURA.getId(), JURA_VOTE.getDate());
        assertThat(byUserIdAndDate).isNotNull()
                .isEqualToComparingFieldByField(JURA_VOTE);
    }

    @Test
    public void getByUserIdAndDateFakeUser() throws Exception {
        Vote byUserIdAndDate = repository.findByUserAndDate(41, LocalDate.now().minusDays(5));
        assertThat(byUserIdAndDate).isNull();
    }

    @Test
    public void save() throws Exception {
        Vote save = repository.save(USER_VOTE);

        assertThat(save).isNotNull();

        Vote byUserIdAndDate = repository.findByUserAndDate(USER_VOTE.getUserId(), USER_VOTE.getDate());
        assertThat(byUserIdAndDate).isEqualToComparingFieldByField(USER_VOTE);
    }

    @Test
    public void getLunchVotesEmpty() throws Exception {
        List<VoteCounter> lunchVotesOnDate = repository.getLunchVoteOnDate(LocalDate.now().minusDays(5));
        assertThat(lunchVotesOnDate).isNotNull().isEmpty();
    }

    @Test
    public void getLunchVotes() throws Exception {
        List<VoteCounter> lunchVotesOnDate = repository.getLunchVoteOnDate(LocalDate.now());
        assertThat(lunchVotesOnDate).hasSize(1);
    }

    @Test
    public void getLunchVotesAfterVote() throws Exception {
        repository.save(USER_VOTE);

        List<VoteCounter> lunchVotesOnDate = repository.getLunchVoteOnDate(LocalDate.now());
        assertThat(lunchVotesOnDate).hasSize(2);
    }


    /*
    ===========================
    =  Test to count queries  =
    ===========================
     */


    @Test
    public void getByUserIdAndDateQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.findByUserAndDate(JURA.getId(), LocalDate.now());
    }

    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.save(SAVE_VOTE_COUNT_QUERY);
    }

    @Test
    public void updateQueryCount() throws Exception {
        countQueries.setLimit(2);
        Vote vote = new Vote(JURA_VOTE);
        vote.setLunchId(CHIKEN.getId());
        repository.save(vote);
    }

    @Test
    public void getLunchVotesOnDateQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.getLunchVoteOnDate(LocalDate.now());
    }
}