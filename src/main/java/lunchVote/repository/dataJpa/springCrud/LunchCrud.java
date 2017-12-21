package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LunchCrud extends JpaRepository<Lunch, Integer> {

    @Modifying
    @Query("DELETE FROM Lunch WHERE id=:id")
    int delete(@Param("id") int id);
}
