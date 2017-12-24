package lunchVote.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity{
    @Column(name = "user_id")
    private int userId;

    @Column(name = "lunch_id")
    private int lunchId;

    @Column(name = "date")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.userId, vote.lunchId, vote.date);
    }

    public Vote(Integer id, int userId, int lunch, @NotNull LocalDate date) {
        super(id);
        this.userId = userId;
        this.lunchId = lunch;
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLunchId() {
        return lunchId;
    }

    public void setLunchId(int lunchId) {
        this.lunchId = lunchId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
