package lunchVote.model;

import java.time.LocalDate;

public class Lunch extends AbstractBaseEntity{
    private LocalDate date;
    private String description;
    private Integer price;
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
