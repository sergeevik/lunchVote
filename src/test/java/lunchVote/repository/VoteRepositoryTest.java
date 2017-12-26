package lunchVote.repository;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.testData.LunchData.CHIKEN;
import static lunchVote.testData.UserData.ADMIN;
import static lunchVote.testData.UserData.JURA;
import static lunchVote.testData.VoteData.ADMIN_VOTE;
import static lunchVote.testData.VoteData.JURA_VOTE;
import static lunchVote.testData.VoteData.USER_VOTE;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteRepositoryTest extends SpringConfigOnTests{

    @Autowired
    private VoteRepository repository;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void getByUserIdAndDateNull() throws Exception {
        Vote byUserIdAndDate = repository.getByUserIdAndDate(ADMIN.getId(), LocalDate.now().minusDays(5));
        assertThat(byUserIdAndDate).isNull();
    }

    @Test
    public void getByUserIdAndDate() throws Exception {
        Vote byUserIdAndDate = repository.getByUserIdAndDate(JURA.getId(), JURA_VOTE.getDate());
        assertThat(byUserIdAndDate).isNotNull()
                .isEqualToComparingFieldByField(JURA_VOTE);
    }

    @Test
    public void getByUserIdAndDateFakeUser() throws Exception {
        Vote byUserIdAndDate = repository.getByUserIdAndDate(41, LocalDate.now().minusDays(5));
        assertThat(byUserIdAndDate).isNull();
    }

    @Test
    public void saveWithFakeUserId() throws Exception {
        exception.expect(DataIntegrityViolationException.class);
        repository.save(42, 41, LocalDate.now());
    }

    @Test
    public void saveNormal() throws Exception {
        Vote save = repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId(), USER_VOTE.getDate());

        assertThat(save).isNotNull();

        Vote byUserIdAndDate = repository.getByUserIdAndDate(USER_VOTE.getUserId(), USER_VOTE.getDate());
        assertThat(byUserIdAndDate).isEqualToComparingFieldByField(USER_VOTE);
    }

    @Test
    public void getLunchVotesEmpty() throws Exception {
        List<VoteCounter> lunchVotesOnDate = repository.getLunchVotesOnDate(LocalDate.now().minusDays(5));
        assertThat(lunchVotesOnDate).isNotNull().isEmpty();
    }

    @Test
    public void getLunchVotes() throws Exception {
        List<VoteCounter> lunchVotesOnDate = repository.getLunchVotesOnDate(LocalDate.now());
        assertThat(lunchVotesOnDate).hasSize(1);
    }

    @Test
    public void getLunchVotesAfterVote() throws Exception {
        repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId(), USER_VOTE.getDate());

        List<VoteCounter> lunchVotesOnDate = repository.getLunchVotesOnDate(LocalDate.now());
        assertThat(lunchVotesOnDate).hasSize(2);
    }

    @Test
    public void updateVote() throws Exception {
        repository.save(CHIKEN.getId(), ADMIN_VOTE.getUserId(), ADMIN_VOTE.getDate());
        Vote adminVote = repository.getByUserIdAndDate(ADMIN_VOTE.getUserId(), ADMIN_VOTE.getDate());
        assertThat(adminVote.getLunchId()).isEqualTo(CHIKEN.getId());
    }

    /*
    ===========================
    =  Test to count queries  =
    ===========================
     */


    @Test
    public void getByUserIdAndDateQueryCount() throws Exception {
        countQueries.setLimit(1);
        repository.getByUserIdAndDate(JURA.getId(), LocalDate.now());
    }

    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(3);
        repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId(), USER_VOTE.getDate());
    }

    @Test
    public void updateQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.save(CHIKEN.getId(), ADMIN_VOTE.getUserId(), ADMIN_VOTE.getDate());
    }

    @Test
    public void getLunchVotesOnDateQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.getLunchVotesOnDate(LocalDate.now());
    }
}