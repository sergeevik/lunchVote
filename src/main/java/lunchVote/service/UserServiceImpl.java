package lunchVote.service;

import lunchVote.model.User;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
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
    @CacheEvict(value = "users", allEntries = true)
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
    @Cacheable("users")
    public List<User> getAll() {
        return crud.findAll();
    }

    @Override
    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public boolean delete(int id) {
        return crud.delete(id)!=0;
    }
}
