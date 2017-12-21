package lunchVote.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
public class Vote extends AbstractBaseEntity{
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "lunch_id")
    private Lunch lunch;

    @Column(name = "date")
    @NotNull
    private LocalDate date = LocalDate.now();

    public Vote() {
    }

    public Vote(Vote vote) {
        this(vote.id, vote.user, vote.lunch, vote.date);
    }

    public Vote(Integer id, User user, Lunch lunch, @NotNull LocalDate date) {
        super(id);
        this.user = user;
        this.lunch = lunch;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lunch getLunch() {
        return lunch;
    }

    public void setLunch(Lunch lunch) {
        this.lunch = lunch;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
