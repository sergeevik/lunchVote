package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Lunch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LunchCrud extends JpaRepository<Lunch, Integer> {
}
