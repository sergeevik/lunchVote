package lunchVote.service;

import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private final VoteCrud crud;

    @Autowired
    public VoteServiceImpl(VoteCrud crud) {
        this.crud = crud;
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
    public List<VoteCounter> getDayResult(LocalDate date) {
        return crud.getLunchVoteOnDate(date);
    }
}
