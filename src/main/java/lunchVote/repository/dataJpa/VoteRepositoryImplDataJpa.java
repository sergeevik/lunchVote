package lunchVote.repository.dataJpa;

import lunchVote.model.Vote;
import lunchVote.repository.VoteRepository;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class VoteRepositoryImplDataJpa implements VoteRepository {
    @Autowired
    private VoteCrud crud;

    @Override
    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        return crud.findByUserAndDate(userId, date);
    }

    @Override
    @Transactional
    public Vote save(int lunchId, int userId, LocalDate date) {
        Vote byUserAndDate = crud.findByUserAndDate(userId, date);
        if (byUserAndDate == null) {
            return crud.save(new Vote(null, userId, lunchId, date));
        }else{
            byUserAndDate.setLunchId(lunchId);
            return crud.save(byUserAndDate);
        }
    }

    @Override
    public List<VoteCounter>  getLunchVotesOnDate(LocalDate date) {
        return crud.getLunchVoteOnDate(date);
    }
}
