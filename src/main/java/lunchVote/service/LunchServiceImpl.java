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

import static lunchVote.util.ValidateUtil.*;

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
    @CacheEvict(value = {"lunch", "vote"}, allEntries = true)
    public Lunch create(LunchTransfer lunch) {
        checkNew(lunch);
        return crud.save(convertToLunch(lunch));
    }

    @Override
    @Transactional
    @CacheEvict(value = {"lunch", "vote"}, allEntries = true)
    public Lunch update(LunchTransfer lunch, int id) {
        checkIdEquals(lunch, id);

        Lunch entity = convertToLunch(lunch);
        Lunch save = crud.save(entity);

        checkEntityNotNull(save, id);
        return save;
    }

    private Lunch convertToLunch(LunchTransfer lunch) {
        Restaurant one = restaurantCrud.getOne(lunch.getRestaurantId());
        Lunch toSave = LunchConverter.fromTo(lunch);
        toSave.setRestaurant(one);
        return toSave;
    }



    @Override
    @Transactional
    @CacheEvict(value = {"lunch", "vote"}, allEntries = true)
    public void delete(int id) {
        boolean delete = crud.delete(id) != 0;
        checkDeleteSuccess(delete, id);
    }

    @Override
    public Lunch get(int id) {
        Lunch lunch = crud.findById(id).orElse(null);
        checkEntityNotNull(lunch, id);
        return lunch;
    }
}
