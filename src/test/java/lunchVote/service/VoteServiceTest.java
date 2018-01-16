package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static lunchVote.testData.VoteData.ADMIN_VOTE;
import static lunchVote.testData.VoteData.SAVE;
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
        Vote voteSave = new Vote(SAVE);
        when(repository.save(voteSave)).thenReturn(voteSave);
        Vote save = service.save(voteSave.getLunchId(), voteSave.getUserId(), voteSave.getDate());

        verify(repository, times(1)).findByUserAndDate(voteSave.getUserId(), voteSave.getDate());
        verify(repository, times(1)).save(voteSave);
        assertThat(save).isNotNull()
                        .isEqualToComparingFieldByField(voteSave);
    }

    @Test
    public void update() throws Exception {
        Vote vote = new Vote(ADMIN_VOTE);
        when(repository.findByUserAndDate(vote.getUserId(), vote.getDate())).thenReturn(vote);
        when(repository.save(vote)).thenReturn(vote);

        Vote save = service.save(vote.getLunchId(), vote.getUserId(), vote.getDate());

        verify(repository, times(1)).findByUserAndDate(vote.getUserId(), vote.getDate());
        verify(repository, times(1)).save(vote);

        assertThat(save).isNotNull().isEqualToComparingFieldByField(vote);
    }

    @Test
    public void getLunchVotes() throws Exception {
        LocalDate now = LocalDate.now();
        service.getDayResult(now);
        verify(repository, times(1)).getLunchVoteOnDate(now);
    }


}