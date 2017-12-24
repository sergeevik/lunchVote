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

import static lunchVote.testData.UserData.ADMIN;
import static lunchVote.testData.UserData.JURA;
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
        Vote byUserIdAndDate = repository.getByUserIdAndDate(JURA.getId(), LocalDate.now());
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
        repository.save(42, 41);
    }

    @Test
    public void saveNormal() throws Exception {
        Vote save = repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId());

        assertThat(save).isNotNull();

        Vote byUserIdAndDate = repository.getByUserIdAndDate(USER_VOTE.getUserId(), save.getDate());
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
        repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId());

        List<VoteCounter> lunchVotesOnDate = repository.getLunchVotesOnDate(LocalDate.now());
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
        repository.getByUserIdAndDate(JURA.getId(), LocalDate.now());
    }

    @Test
    public void saveQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.save(USER_VOTE.getLunchId(), USER_VOTE.getUserId());
    }

    @Test
    public void getLunchVotesOnDateQueryCount() throws Exception {
        countQueries.setLimit(2);
        repository.getLunchVotesOnDate(LocalDate.now());
    }
}