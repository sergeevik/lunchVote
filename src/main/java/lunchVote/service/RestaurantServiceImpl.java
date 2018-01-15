package lunchVote.service;

import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Restaurant save(Restaurant restaurant) {
        return crud.save(restaurant);
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
        return crud.findById(id).orElse(null);
    }
}
