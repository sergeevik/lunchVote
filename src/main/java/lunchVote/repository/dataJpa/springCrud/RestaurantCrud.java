package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantCrud extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Query("DELETE FROM Restaurant WHERE id=:id")
    int delete(@Param("id") int id);
}
