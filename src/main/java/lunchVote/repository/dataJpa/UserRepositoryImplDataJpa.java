package lunchVote.repository.dataJpa;

import lunchVote.model.User;
import lunchVote.repository.UserRepository;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepositoryImplDataJpa implements UserRepository {

    @Autowired
    private UserCrud crud;

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User save(User object) {
        return null;
    }

    @Override
    public User getById(int id) {
        return crud.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return crud.getAllWithRoles();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
