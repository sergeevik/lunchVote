package lunchVote.service;

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
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class VoteServiceImpl implements VoteService {
    private final VoteCrud crud;
    private Cache cacheVote;

    @Autowired
    public VoteServiceImpl(VoteCrud crud, CacheManager cacheManager) {
        this.crud = crud;
        cacheVote = cacheManager.getCache("vote");
    }

    @Override
    @Transactional
    public Vote save(int lunchId, int userId, LocalDate date) {
        Vote toSave = new Vote(null, userId, lunchId, date);

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

    @Override
    public List<VoteCounter> getDayResult(LocalDate date) {
        return crud.getLunchVoteOnDate(date);
    }
}
