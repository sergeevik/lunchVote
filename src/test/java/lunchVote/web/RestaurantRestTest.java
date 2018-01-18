package lunchVote.web;

import lunchVote.model.Restaurant;
import lunchVote.service.RestaurantService;
import lunchVote.web.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static lunchVote.testData.RestaurantData.*;
import static lunchVote.utils.TestDataUtil.toList;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestTest extends MvcConfig{
    private static final String URL = RestaurantRest.URL;

    private RestaurantRest rest;
    private RestaurantService service;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        service = mock(RestaurantService.class);
        rest = new RestaurantRest(service);
        mockMvc = super.mockMvcWithController(rest);
    }

    @Test
    public void createTest() throws Exception {
        Restaurant restaurant = new Restaurant(SAVE_NEW);


        when(service.create(restaurant)).thenReturn(restaurant);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(restaurant)));

        verify(service, times(1)).create(restaurant);
    }

    @Test
    public void updateTest() throws Exception {
        Restaurant restaurant = new Restaurant(KFC);
        restaurant.setName("MVC Update");

        when(service.update(restaurant, restaurant.getId())).thenReturn(restaurant);

        mockMvc.perform(put(URL + "/" + restaurant.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andExpect(status().isOk())
                .andDo(print());

        verify(service, times(1)).update(restaurant, restaurant.getId());

    }

    @Test
    public void deleteTest() throws Exception {
        int id = KFC.getId();
        mockMvc.perform(delete(URL + "/" + id))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(service, times(1)).delete(id);
    }

    @Test
    public void getTest() throws Exception {
        getOneRestaurant(KFC);
        getOneRestaurant(BURGER_KING);
    }

    @Test
    public void getAllTest() throws Exception {
        when(service.getAll()).thenReturn(toList(getAllRestaurant()));

        getURLReturnData(URL, getAllRestaurant());

        verify(service, times(1)).getAll();
    }

    private void getOneRestaurant(Restaurant restaurant) throws Exception {
        when(service.get(restaurant.getId())).thenReturn(restaurant);

        getURLReturnData(URL + "/" + restaurant.getId(), restaurant);

        verify(service, times(1)).get(restaurant.getId());
    }

    private void getURLReturnData( String url, Object returnValue) throws Exception {
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(returnValue)));
    }
}