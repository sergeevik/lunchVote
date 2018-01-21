package lunchVote.web.security;

import lunchVote.model.Lunch;
import lunchVote.transferObjects.LunchTransfer;
import lunchVote.util.LunchConverter;
import lunchVote.web.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static lunchVote.testData.LunchData.*;
import static lunchVote.testData.UserData.ADMIN;
import static lunchVote.testData.UserData.USER;
import static lunchVote.utils.TestDataUtil.toList;
import static lunchVote.web.LunchRest.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LunchRestSecurityTest extends SecurityConfig {

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = mockToSecurity();
    }

    @Test
    public void getLunchUser() throws Exception {
        int id = BIG_MACK.getId();
        mockMvc.perform(get(URL + "/" + id)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(BIG_MACK)));
    }

    @Test
    public void getLunchFakeAuth() throws Exception {
        int id = BIG_MACK.getId();
        mockMvc.perform(get(URL + "/" + id)
                .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void createLunchAdmin() throws Exception {
        LunchTransfer to = LunchConverter.asTo(SAVE_NEW);

        mockMvc.perform(post(URL)
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    public void createLunchUser() throws Exception {
        LunchTransfer to = LunchConverter.asTo(SAVE_NEW);

        mockMvc.perform(post(URL)
                        .with(userHttpBasic(USER))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isForbidden());

    }

    @Test
    public void createLunchFakeAuth() throws Exception {
        LunchTransfer to = LunchConverter.asTo(SAVE_NEW);

        mockMvc.perform(post(URL)
                        .with(fakeHttpBasic())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

    @Test
    public void updateLunchAdmin() throws Exception {
        Lunch lunch = new Lunch(VOPER);

        LunchTransfer to = LunchConverter.asTo(lunch);

        mockMvc.perform(put(URL + "/" + lunch.getId())
                        .with(userHttpBasic(ADMIN))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateLunchUser() throws Exception {
        Lunch lunch = new Lunch(VOPER);

        LunchTransfer to = LunchConverter.asTo(lunch);

        mockMvc.perform(put(URL + "/" + lunch.getId())
                        .with(userHttpBasic(USER))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void updateLunchFakeAuth() throws Exception {
        Lunch lunch = new Lunch(VOPER);

        LunchTransfer to = LunchConverter.asTo(lunch);

        mockMvc.perform(put(URL + "/" + lunch.getId())
                        .with(fakeHttpBasic())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void deleteLunchAdmin() throws Exception {
        int id = VOPER.getId();
        mockMvc.perform(delete(URL + "/" + id)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void deleteLunchUser() throws Exception {
        int id = VOPER.getId();
        mockMvc.perform(delete(URL + "/" + id)
                        .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteLunchFakeAuth() throws Exception {
        int id = VOPER.getId();
        mockMvc.perform(delete(URL + "/" + id)
                        .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void getOnDayUser() throws Exception {
        List<Lunch> lunches = toList(allLunch());
        LocalDate findDay = LocalDate.now();

        mockMvc.perform(get(URL + "?date=" + findDay.toString())
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(lunches)));

    }

    @Test
    public void getOnDayFakeAuth() throws Exception {
        LocalDate findDay = LocalDate.of(2018, 1, 1);

        mockMvc.perform(get(URL + "?date=" + findDay.toString())
                        .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());

    }

}
