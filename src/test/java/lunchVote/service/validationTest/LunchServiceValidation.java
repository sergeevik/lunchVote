package lunchVote.service.validationTest;

import lunchVote.SpringConfigOnTests;
import lunchVote.exceptions.IdNotEqualsException;
import lunchVote.exceptions.NotFoundEntity;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.service.LunchService;
import lunchVote.service.LunchServiceImpl;
import lunchVote.testData.LunchData;
import lunchVote.transferObjects.LunchTransfer;
import lunchVote.util.LunchConverter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class LunchServiceValidation extends SpringConfigOnTests {

    private LunchService service;
    private LunchCrud lunchCrud;
    private RestaurantCrud restaurantCrud;

    @Before
    public void setUp() throws Exception {
        lunchCrud = mock(LunchCrud.class);
        restaurantCrud = mock(RestaurantCrud.class);
        service = new LunchServiceImpl(lunchCrud, restaurantCrud);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createNotNew(){
        LunchTransfer to = LunchConverter.asTo(LunchData.VOPER);
        service.create(to);
    }


    @Test(expected = IdNotEqualsException.class)
    public void updateFailId(){
        LunchTransfer to = LunchConverter.asTo(LunchData.VOPER);
        int randomId = 11;
        service.update(to, randomId);
    }

    @Test(expected = NotFoundEntity.class)
    public void updateNotExistLunch(){
        int randomId = 11;
        LunchTransfer to = new LunchTransfer(randomId, "", 11.1, null, 0);
        service.update(to, randomId);
    }


    @Test(expected = NotFoundEntity.class)
    public void getNotExistLunch(){
        int randomId = 11;
        service.get(randomId);
    }
}
