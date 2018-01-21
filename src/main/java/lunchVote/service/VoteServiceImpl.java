package lunchVote.service;

import lunchVote.exceptions.NotTodayLunchException;
import lunchVote.exceptions.TooLateToVoteException;
import lunchVote.model.Lunch;
import lunchVote.model.Vote;
import lunchVote.repository.dataJpa.springCrud.VoteCrud;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private static final LocalTime endVote = LocalTime.of(12, 0);
    private final VoteCrud crud;
    private final LunchService lunchService;

    private Cache cacheVote;

    @Autowired
    public VoteServiceImpl(VoteCrud crud, LunchService lunchService, CacheManager cacheManager) {
        this.crud = crud;
        this.lunchService = lunchService;
        cacheVote = cacheManager.getCache("vote");
    }

    @Override
    @Transactional
    public Vote save(int lunchId, int userId, LocalDateTime dateTime) {
        Vote toSave = new Vote(null, userId, lunchId, dateTime.toLocalDate());

        checkLunchToday(lunchId, dateTime.toLocalDate());
        checkTime(dateTime.toLocalTime());

        ValueWrapper valueWrapper = cacheVote.get(userId);
        if (valueWrapper != null){
            Integer id = (Integer) valueWrapper.get();
            toSave.setId(id);
            crud.update(id, toSave.getLunchId());
            return toSave;
        }else {
            Vote newSave = crud.save(toSave);
            cacheVote.put(newSave.getUserId(), newSave.getId());
            return newSave;
        }
    }

    private void checkTime(LocalTime time) {
        if (time.isAfter(endVote))
            throw new TooLateToVoteException();
    }

    private void checkLunchToday(int lunchId, LocalDate date) {
        Lunch lunch = lunchService.get(lunchId);
        if (!lunch.getDate().equals(date))
            throw new NotTodayLunchException();
    }

    @Override
    public List<VoteCounter> getDayResult(LocalDate date) {
        return crud.getLunchVoteOnDate(date);
    }
}
