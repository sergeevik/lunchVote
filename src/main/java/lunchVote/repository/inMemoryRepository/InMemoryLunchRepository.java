package lunchVote.repository.inMemoryRepository;

import lunchVote.model.Lunch;
import lunchVote.repository.LunchRepository;
import lunchVote.repository.testData.LunchData;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryLunchRepository implements LunchRepository {
    private static final Map<Integer, Lunch> repo = new HashMap<>();
    private static AtomicInteger counter;

    public static void initRepo() {
        repo.clear();
        repo.put(1, LunchData.VOPER);
        repo.put(2, LunchData.BIG_MACK);
        repo.put(3, LunchData.CHIKEN);
        counter = new AtomicInteger(repo.size());
    }

    @Override
    public List<Lunch> getAllForDate(LocalDate date) {
        return null;
    }

    @Override
    public Lunch save(Lunch object) {
        object.setId(1);
        return object;
    }

    @Override
    public Lunch getById(int id) {
        return null;
    }

    @Override
    public List<Lunch> getAll() {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
