package lunchVote.service;

import lunchVote.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);
    Restaurant update(Restaurant restaurant, int restaurantId);
    void delete(int id);
    Restaurant get(int id);
    List<Restaurant> getAll();
}
