package lunchVote.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "vote")
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
        this.id = id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote vote = (Vote) o;

        return Objects.equals(id, vote.id) &&
                Objects.equals(userId, vote.userId) &&
                Objects.equals(lunchId, vote.lunchId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, lunchId);
    }
}
