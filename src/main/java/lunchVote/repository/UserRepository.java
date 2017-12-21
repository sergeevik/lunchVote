package lunchVote.repository;

import lunchVote.model.User;

public interface UserRepository extends MyCrudRepository<User>{
    User getByEmail(String email);
}
