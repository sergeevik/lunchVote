package lunchVote.service;

import lunchVote.model.Lunch;
import lunchVote.transferObjects.LunchTransfer;

import java.time.LocalDate;
import java.util.List;

public interface LunchService {
    Lunch create(LunchTransfer lunch);
    Lunch update(LunchTransfer lunch, int lunchId);
    boolean delete(int id);
    Lunch get(int id);
    List<Lunch> getAllForDate(LocalDate date);
}
