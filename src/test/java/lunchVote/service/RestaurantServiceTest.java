package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.Restaurant;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.testData.RestaurantData;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static lunchVote.testData.RestaurantData.*;
import static org.mockito.Mockito.*;


public class RestaurantServiceTest extends SpringConfigOnTests {

    private RestaurantService service;
    private RestaurantCrud repository;

    @Before
    public void setUp() throws Exception {
        repository = mock(RestaurantCrud.class);
        service = new RestaurantServiceImpl(repository);
    }

    @Test
    public void create() throws Exception {
        Restaurant restaurant = new Restaurant(SAVE_NEW);
        service.create(restaurant);
        verify(repository, times(1)).save(restaurant);
    }

    @Test
    public void getAll() throws Exception {
        service.getAll();
        verify(repository, times(1)).findAll();
    }

    @Test
    public void update() throws Exception {
        Restaurant restaurant = new Restaurant(MC_DONALD);
        restaurant.setName("Sushi");
        when(repository.save(restaurant)).thenReturn(restaurant);

        service.update(restaurant, restaurant.getId());
        verify(repository, times(1)).save(restaurant);
    }


    @Test
    public void delete() throws Exception {
        Integer id = BURGER_KING.getId();
        when(repository.delete(id)).thenReturn(1);

        service.delete(id);
        verify(repository, times(1)).delete(id);
    }

    @Test
    public void getById() throws Exception {
        Integer id = BURGER_KING.getId();
        when(repository.findById(id)).thenReturn(Optional.of(BURGER_KING));
        service.get(id);
        verify(repository, times(1)).findById(id);
    }



}