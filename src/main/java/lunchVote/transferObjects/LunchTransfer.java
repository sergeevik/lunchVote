package lunchVote.transferObjects;

import java.time.LocalDate;

public class LunchTransfer {
    private Integer id;
    private String description;
    private Double price;
    private LocalDate date;
    private int restaurantId;

    public LunchTransfer(Integer id, String description, Double price, LocalDate date, int restaurantId) {
        this.id = id;
        this.description = description;
        this.price = price;
        this.date = date;
        this.restaurantId = restaurantId;
    }

    public LunchTransfer() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
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
}
