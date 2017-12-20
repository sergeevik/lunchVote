package lunchVote.repository;

import java.util.List;

public interface MyCrudRepository<T> {
    T save(T object);
    T getById(int id);
    List<T> getAll();
    boolean delete(int id);

}
