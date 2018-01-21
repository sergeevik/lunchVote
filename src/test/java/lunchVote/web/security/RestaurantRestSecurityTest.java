package lunchVote.web.security;

import lunchVote.model.Restaurant;
import lunchVote.web.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static lunchVote.testData.RestaurantData.*;
import static lunchVote.testData.UserData.ADMIN;
import static lunchVote.testData.UserData.USER;
import static lunchVote.utils.TestDataUtil.toList;
import static lunchVote.web.RestaurantRest.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantRestSecurityTest extends SecurityConfig{

    private MockMvc mockMvc;
    private int entityId = KFC.getId();

    @Before
    public void setUp() throws Exception {
        mockMvc = mockToSecurity();
    }

    @Test
    public void getRestaurantUser() throws Exception {

        mockMvc.perform(get(URL + "/" + entityId)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(KFC)));
    }

    @Test
    public void getRestaurantFakeAuth() throws Exception {

        mockMvc.perform(get(URL + "/" + entityId)
                .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createRestaurantAdmin() throws Exception {
        Restaurant restaurant = new Restaurant(SAVE_NEW);

        mockMvc.perform(post(URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void createRestaurantUser() throws Exception {
        Restaurant restaurant = new Restaurant(SAVE_NEW);

        mockMvc.perform(post(URL)
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    public void createRestaurantFakeAuth() throws Exception {
        Restaurant restaurant = new Restaurant(SAVE_NEW);

        mockMvc.perform(post(URL)
                .with(fakeHttpBasic())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void updateRestaurantAdmin() throws Exception {
        Restaurant restaurant = new Restaurant(KFC);

        mockMvc.perform(put(URL + "/" + restaurant.getId())
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateRestaurantUser() throws Exception {
        Restaurant restaurant = new Restaurant(KFC);

        mockMvc.perform(put(URL + "/" + restaurant.getId())
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateRestaurantFakeAuth() throws Exception {
        Restaurant restaurant = new Restaurant(KFC);

        mockMvc.perform(put(URL + "/" + restaurant.getId())
                        .with(fakeHttpBasic())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(restaurant)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteRestaurantAdmin() throws Exception {

        mockMvc.perform(delete(URL + "/" + entityId)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteRestaurantUser() throws Exception {

        mockMvc.perform(delete(URL + "/" + entityId)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteRestaurantFakeAuth() throws Exception {

        mockMvc.perform(delete(URL + "/" + entityId)
                .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getAll() throws Exception {

        mockMvc.perform(get(URL)
                        .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(toList(getAllRestaurant()))));

    }

    @Test
    public void getAllFakeAuth() throws Exception {

        mockMvc.perform(get(URL)
                        .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

}
