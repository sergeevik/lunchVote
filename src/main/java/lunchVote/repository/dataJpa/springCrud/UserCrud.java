package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserCrud extends JpaRepository<User, Integer>{

    @Query("SELECT DISTINCT user FROM User user LEFT JOIN FETCH user.roles")
    List<User> getAllWithRoles();
}
