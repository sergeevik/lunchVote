package lunchVote.service;

import lunchVote.model.Lunch;
import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.transferObjects.LunchTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
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
    public List<Lunch> getAllForDate(LocalDate date) {
        return crud.getByDate(date);
    }

    @Override
    @Transactional
    public Lunch save(LunchTransfer lunch) {
        Restaurant one = restaurantCrud.getOne(lunch.getRestaurantId());
        Integer price = new Double(lunch.getPrice() * 100).intValue();
        Lunch toSave = new Lunch(lunch.getId(), lunch.getDate(), lunch.getDescription(), price, one);
        return crud.save(toSave);
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crud.delete(id) != 0;
    }

    @Override
    public Lunch get(int id) {
        return crud.findById(id).orElse(null);
    }
}
