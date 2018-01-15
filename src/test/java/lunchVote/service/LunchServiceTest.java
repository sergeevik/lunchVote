package lunchVote.service;

import lunchVote.SpringConfigOnTests;
import lunchVote.model.Lunch;
import lunchVote.repository.dataJpa.springCrud.LunchCrud;
import lunchVote.repository.dataJpa.springCrud.RestaurantCrud;
import lunchVote.transferObjects.LunchTransfer;
import lunchVote.util.LunchConverter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static lunchVote.testData.LunchData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class LunchServiceTest extends SpringConfigOnTests {

    private LunchService service;
    private LunchCrud lunchCrud;
    private RestaurantCrud restaurantCrud;

    @Before
    public void setUp() throws Exception {
        lunchCrud = mock(LunchCrud.class);
        restaurantCrud = mock(RestaurantCrud.class);
        service = new LunchServiceImpl(lunchCrud, restaurantCrud);
        initMockCrud();
    }

    public void initMockCrud(){
        when(lunchCrud.findById(BIG_MACK.getId())).thenReturn(Optional.of(BIG_MACK));
        when(lunchCrud.delete(CHIKEN.getId())).thenReturn(1);
    }

    @Test
    public void save() throws Exception {
        LunchTransfer lunch = LunchConverter.asTo(SAVE_NEW);
        when(lunchCrud.save(SAVE_NEW)).thenReturn(SAVE_NEW);

        Lunch save = service.save(lunch);

        verify(restaurantCrud, times(1)).getOne(SAVE_NEW.getRestaurant().getId());
        verify(lunchCrud, times(1)).save(SAVE_NEW);
        assertThat(save).isEqualTo(SAVE_NEW);

    }

    @Test
    public void getById() throws Exception {
        Lunch lunch = service.get(BIG_MACK.getId());
        verify(lunchCrud, times(1)).findById(BIG_MACK.getId());
        assertThat(lunch).isEqualToIgnoringGivenFields(BIG_MACK, "restaurant");
    }

    @Test
    public void getByFakeId() throws Exception {
        int fakeId = 12;
        Lunch lunch = service.get(fakeId);
        verify(lunchCrud, times(1)).findById(fakeId);
        assertThat(lunch).isNull();
    }

    @Test
    public void update() throws Exception {
        Lunch lunch = new Lunch(VOPER);
        lunch.setPrice(200000);
        LunchTransfer to = LunchConverter.asTo(lunch);
        when(lunchCrud.save(lunch)).thenReturn(lunch);

        Lunch save = service.save(to);

        verify(restaurantCrud, times(1)).getOne(lunch.getRestaurant().getId());
        verify(lunchCrud, times(1)).save(lunch);
        assertThat(save).isEqualTo(lunch);

    }

    @Test
    public void deleteExist() throws Exception {
        boolean delete = service.delete(CHIKEN.getId());
        verify(lunchCrud, times(1)).delete(CHIKEN.getId());
        assertThat(delete).isTrue();
    }

    @Test
    public void deleteNotExist() throws Exception {
        int fakeId = -177;
        boolean delete = service.delete(fakeId);
        verify(lunchCrud, times(1)).delete(fakeId);
        assertThat(delete).isFalse();
    }

    @Test
    public void getByDay() throws Exception {
        LocalDate date = LocalDate.of(2017, 2, 19);
        service.getAllForDate(date);
        verify(lunchCrud, times(1)).getByDate(date);
    }



}