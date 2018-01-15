package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.transferObjects.VoteCounter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.testData.VoteData.USER_VOTE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class VoteServiceTest extends SpringConfigOnTests {

    private VoteService service;
    private VoteCrud repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(VoteCrud.class);
        service = new VoteServiceImpl(repository);
    }

    @Test
    public void save() throws Exception {
        Vote voteSave = new Vote(USER_VOTE);
        voteSave.setId(null);
        when(repository.save(voteSave)).thenReturn(voteSave);
        Vote save = service.save(voteSave.getLunchId(), voteSave.getUserId(), voteSave.getDate());

        verify(repository, times(1)).save(voteSave);
        assertThat(save).isNotNull()
                        .isEqualTo(voteSave);
    }

    @Test
    public void getLunchVotes() throws Exception {
        LocalDate now = LocalDate.now();
        List<VoteCounter> lunchVotesOnDate = service.getDayResult(now);
        verify(repository, times(1)).getLunchVoteOnDate(now);
    }


}