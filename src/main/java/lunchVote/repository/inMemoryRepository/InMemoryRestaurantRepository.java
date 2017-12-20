package lunchVote.repository.inMemoryRepository;

import lunchVote.model.Restaurant;
import lunchVote.repository.RestaurantRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static lunchVote.repository.testData.RestaurantData.*;

@Repository
public class InMemoryRestaurantRepository implements RestaurantRepository {
    private static Map<Integer, Restaurant> repo = new HashMap<>();

    public static void initTestData() {
        repo.clear();
        repo.put(1, RESTAURANT1);
        repo.put(2, RESTAURANT2);
        repo.put(3, RESTAURANT3);
        counter = new AtomicInteger(repo.size());
    }

    private static AtomicInteger counter = new AtomicInteger();

    @Override
    public Restaurant save(Restaurant object) {
        if (object.isNew()) {
            int id = counter.incrementAndGet();
            object.setId(id);
            repo.put(id, object);
            return object;
        }else if (!repo.containsKey(object.getId())){
            return null;
        }
        repo.put(object.getId(), object);
        return object;
    }

    @Override
    public Restaurant getById(int id) {
        return repo.get(id);
    }

    @Override
    public List<Restaurant> getAll() {
        return new ArrayList<>(repo.values());
    }

    @Override
    public boolean delete(int id) {
        return repo.remove(id) != null;
    }
}
