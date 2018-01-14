package lunchVote.service;

import lunchVote.model.User;

import java.util.List;

public interface UserService {
    User save(User object);
    User getById(int id);
    User getByEmail(String email);
    List<User> getAll();
    boolean delete(int id);
}
