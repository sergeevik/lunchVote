package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LunchCrud extends JpaRepository<Lunch, Integer> {

    @Modifying
    @Query("DELETE FROM Lunch WHERE id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT lunch FROM Lunch lunch LEFT JOIN FETCH lunch.restaurant WHERE lunch.date=:date")
    List<Lunch> getByDate(@Param("date") LocalDate date);

    @Override
    @Query("SELECT lunch FROM Lunch lunch LEFT JOIN FETCH lunch.restaurant")
    List<Lunch> findAll();
}
