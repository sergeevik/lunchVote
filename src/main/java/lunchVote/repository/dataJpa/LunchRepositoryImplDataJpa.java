package lunchVote.repository.dataJpa;

import lunchVote.model.Lunch;
import lunchVote.repository.LunchRepository;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class LunchRepositoryImplDataJpa implements LunchRepository {

    @Autowired
    LunchCrud crud;

    @Override
    public List<Lunch> getAllForDate(LocalDate date) {
        return null;
    }

    @Override
    public Lunch save(Lunch object) {
        if (!object.isNew() && getById(object.getId()) == null)
            return null;
        return crud.save(object);
    }

    @Override
    public Lunch getById(int id) {
        return crud.findById(id).orElse(null);
    }

    @Override
    public List<Lunch> getAll() {
        return crud.findAll();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
