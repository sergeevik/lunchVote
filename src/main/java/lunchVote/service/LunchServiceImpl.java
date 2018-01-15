package lunchVote.service;

import lunchVote.model.Lunch;
import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.transferObjects.LunchTransfer;
import lunchVote.util.LunchConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class LunchServiceImpl implements LunchService{

    private final LunchCrud crud;

    private final RestaurantCrud restaurantCrud;

    @Autowired
    public LunchServiceImpl(LunchCrud crud, RestaurantCrud restaurantCrud) {
        this.crud = crud;
        this.restaurantCrud = restaurantCrud;
    }

    @Override
    @Cacheable("lunch")
    public List<Lunch> getAllForDate(LocalDate date) {
        return crud.getByDate(date);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lunch", allEntries = true)
    public Lunch save(LunchTransfer lunch) {
        Restaurant one = restaurantCrud.getOne(lunch.getRestaurantId());
        Lunch toSave = LunchConverter.fromTo(lunch);
        toSave.setRestaurant(one);
        return crud.save(toSave);
    }

    @Override
    @Transactional
    @CacheEvict(value = "lunch", allEntries = true)
    public boolean delete(int id) {
        return crud.delete(id) != 0;
    }

    @Override
    public Lunch get(int id) {
        return crud.findById(id).orElse(null);
    }
}
