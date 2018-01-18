package lunchVote.model;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Entity
@Table(name = "lunch")
public class Lunch extends AbstractBaseEntity implements HasId{

    @Column(name = "date")
    @NotNull
    private LocalDate date = LocalDate.now();

    @Column(name = "description")
    @NotBlank
    @Size(max = 255)
    private String description;

    @Column(name = "price")
    @NotNull
    @Range(min = 100)
    private int price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    public Lunch(Integer id, LocalDate date, String description, Integer price, Restaurant restaurant) {
        super(id);
        this.date = date;
        this.description = description;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Lunch(Lunch lunch) {
        this(lunch.id, lunch.date, lunch.description, lunch.price, lunch.restaurant);
    }

    public Lunch() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Lunch{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
