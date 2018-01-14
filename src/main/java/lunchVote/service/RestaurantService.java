package lunchVote.service;

import lunchVote.model.Restaurant;

import java.util.List;

public interface RestaurantService {
    Restaurant save(Restaurant restaurant);
    boolean delete(int id);
    Restaurant get(int id);
    List<Restaurant> getAll();
}
