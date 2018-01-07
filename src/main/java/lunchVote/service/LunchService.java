package lunchVote.service;

import lunchVote.model.Lunch;
import lunchVote.transferObjects.LunchTransfer;

public interface LunchService {
    Lunch create(LunchTransfer lunch);
    Lunch update(LunchTransfer lunch);
    boolean delete(int id);
    Lunch get(int id);
}
