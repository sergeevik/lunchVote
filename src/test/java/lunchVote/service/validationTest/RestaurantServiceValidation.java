package lunchVote.service.validationTest;

import lunchVote.SpringConfigOnTests;
import lunchVote.exceptions.IdNotEqualsException;
import lunchVote.exceptions.NotFoundEntity;
import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.service.RestaurantService;
import lunchVote.service.RestaurantServiceImpl;
import org.junit.Before;
import org.junit.Test;

import static lunchVote.testData.RestaurantData.*;
import static org.mockito.Mockito.mock;

public class RestaurantServiceValidation extends SpringConfigOnTests {

    private RestaurantService service;
    private RestaurantCrud repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(RestaurantCrud.class);
        service = new RestaurantServiceImpl(repository);
    }


    @Test(expected = IllegalArgumentException.class)
    public void createNotNew(){
        service.create(MC_DONALD);
    }


    @Test(expected = IdNotEqualsException.class)
    public void updateFailId(){
        int randomId = 11;
        service.update(KFC, randomId);

    }

    @Test(expected = NotFoundEntity.class)
    public void updateNotExistRestaurant(){
        int randomId = 11;
        Restaurant notFound = new Restaurant(randomId,"","");
        service.update(notFound, randomId);
    }


    @Test(expected = NotFoundEntity.class)
    public void getNotExistRestaurant(){
        int randomId = 11;
        service.get(randomId);
    }

}
