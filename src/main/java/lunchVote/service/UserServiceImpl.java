package lunchVote.service;

import lunchVote.AuthUser;
import lunchVote.model.User;
import lunchVote.repository.dataJpa.springCrud.UserCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = crud.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthUser(user);
    }
}
