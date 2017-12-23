package lunchVote.repository.dataJpa;

import lunchVote.model.Lunch;
import lunchVote.model.User;
import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;
import lunchVote.repository.VoteRepository;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
@Transactional(readOnly = true)
public class VoteRepositoryImplDataJpa implements VoteRepository {
    @Autowired
    private VoteCrud crud;

    @Autowired
    private UserCrud userCrud;

    @Autowired
    private LunchCrud lunchCrud;

    @Override
    public Vote getByUserIdAndDate(int userId, LocalDate date) {
        return crud.findByUserAndDate(userId, date);
    }

    @Override
    @Transactional
    public Vote save(int lunchId, int userId) {
        Lunch lunch = lunchCrud.getOne(lunchId);
        User user = userCrud.getOne(userId);
        return crud.save(new Vote(null, user, lunch, LocalDate.now()));
    }

    @Override
    public List<VoteCounter>  getLunchVotesOnDate(LocalDate date) {
        return crud.getLunchVoteOnDate(date);
    }
}
