package lunchVote.service;

import lunchVote.model.User;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserCrud crud;

    @Autowired
    public UserServiceImpl(UserCrud crud) {
        this.crud = crud;
    }

    @Override
    public User getByEmail(String email) {
        return crud.getByEmail(email);
    }

    @Override
    @Transactional
    public User save(User object) {
        if(!object.isNew() && getById(object.getId()) == null)
            return null;
        return crud.save(object);
    }

    @Override
    public User getById(int id) {
        return crud.findById(id).orElse(null);
    }

    @Override
    public List<User> getAll() {
        return crud.findAll();
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return crud.delete(id)!=0;
    }
}
