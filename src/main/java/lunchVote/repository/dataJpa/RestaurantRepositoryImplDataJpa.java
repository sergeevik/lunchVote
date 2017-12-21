package lunchVote.repository.dataJpa;

import lunchVote.model.Restaurant;
import lunchVote.repository.RestaurantRepository;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class RestaurantRepositoryImplDataJpa implements RestaurantRepository {

    @Autowired
    private RestaurantCrud crud;

    @Override
    @Transactional
    public Restaurant save(Restaurant object) {
        if (!object.isNew() && getById(object.getId()) == null) {
            return null;
        }else {
            return crud.save(object);
        }
    }

    @Override
    public Restaurant getById(int id) {
        return crud.findById(id).orElse(null);
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
}
