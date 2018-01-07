package lunchVote.service;

import lunchVote.model.Restaurant;

public interface RestaurantService {
    Restaurant create(Restaurant restaurant);
    Restaurant update(Restaurant restaurant);
    boolean delete(int id);
    Restaurant get(int id);
}
