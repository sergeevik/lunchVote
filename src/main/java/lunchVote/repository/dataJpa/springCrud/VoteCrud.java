package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VoteCrud extends JpaRepository<Vote, Integer>{

    @Query("SELECT DISTINCT vote FROM Vote vote WHERE vote.user.id=:userId And vote.date=:date")
    Vote findByUserAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("select new lunchVote.transferObjects.VoteCounter(vote.lunch, count(vote)) FROM Vote vote where vote.date=:date GROUP BY vote.lunch")
    List<VoteCounter> getLunchVoteOnDate(@Param("date") LocalDate date);


}
