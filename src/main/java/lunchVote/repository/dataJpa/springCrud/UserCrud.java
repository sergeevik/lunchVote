package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCrud extends JpaRepository<User, Integer>{

}
