package lunchVote.service;

import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static lunchVote.util.ValidateUtil.checkEntityNotNull;
import static lunchVote.util.ValidateUtil.checkIdEquals;
import static lunchVote.util.ValidateUtil.checkNew;

@Service
@Transactional(readOnly = true)
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantCrud crud;

    @Autowired
    public RestaurantServiceImpl(RestaurantCrud crud) {
        this.crud = crud;
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        checkNew(restaurant);
        return crud.save(restaurant);
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public Restaurant update(Restaurant restaurant, int restaurantId) {
        checkIdEquals(restaurant, restaurantId);
        Restaurant save = crud.save(restaurant);
        checkEntityNotNull(save, restaurantId);
        return save;
    }

    @Override
    @Cacheable("restaurant")
    public List<Restaurant> getAll() {
        return crud.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "restaurant", allEntries = true)
    public boolean delete(int id) {
        return crud.delete(id) != 0;
    }

    @Override
    public Restaurant get(int id) {
        Restaurant restaurant = crud.findById(id).orElse(null);
        checkEntityNotNull(restaurant, id);
        return restaurant;
    }
}
