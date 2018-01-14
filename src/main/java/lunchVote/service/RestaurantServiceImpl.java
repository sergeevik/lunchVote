package lunchVote.service;

import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantCrud crud;

    @Autowired
    public RestaurantServiceImpl(RestaurantCrud crud) {
        this.crud = crud;
    }

    @Override
    @Transactional
    public Restaurant save(Restaurant restaurant) {
        if (!restaurant.isNew() && get(restaurant.getId()) == null) {
            return null;
        }else {
            return crud.save(restaurant);
        }
    }

    @Override
    public List<Restaurant> getAll() {
        return crud.findAll();
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crud.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        return crud.findById(id).orElse(null);
    }
}
