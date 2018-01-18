package lunchVote.repository.dataJpa.springCrud;

import lunchVote.model.Vote;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface VoteCrud extends JpaRepository<Vote, Integer>{

    @Query("SELECT DISTINCT vote FROM Vote vote WHERE vote.userId=:userId And vote.date=:date")
    Vote findByUserAndDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("select new lunchVote.transferObjects.VoteCounter(lunch, count(vote))" +
            " FROM Vote vote LEFT JOIN FETCH Lunch lunch ON vote.lunchId=lunch.id" +
            " where vote.date=:date GROUP BY lunch")
    List<VoteCounter> getLunchVoteOnDate(@Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query("UPDATE Vote vote SET vote.lunchId=:lunchId WHERE vote.userId=:userId And vote.date=:date")
    int update(@Param("userId") int userId,
                @Param("lunchId") int lunchId,
                @Param("date") LocalDate date);
}
