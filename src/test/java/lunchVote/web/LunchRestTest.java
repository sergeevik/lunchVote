package lunchVote.web;

import lunchVote.model.Lunch;
import lunchVote.service.LunchService;
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
import static lunchVote.utils.TestDataUtil.toList;
import static lunchVote.web.LunchRest.URL;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LunchRestTest extends MvcConfig{

    private LunchRest rest;
    private LunchService service;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        service = mock(LunchService.class);
        rest = new LunchRest(service);
        mockMvc = super.mockMvcWithController(rest);
    }

    @Test
    public void create() throws Exception {

        LunchTransfer to = LunchConverter.asTo(SAVE_NEW);
        when(service.create(to)).thenReturn(SAVE_NEW);

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(SAVE_NEW)));

        verify(service, times(1)).create(to);
    }

    @Test
    public void update() throws Exception {
        Lunch lunch = new Lunch(VOPER);

        LunchTransfer to = LunchConverter.asTo(lunch);
        when(service.update(to, to.getId())).thenReturn(lunch);

        mockMvc.perform(put(URL + "/" + lunch.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(JsonUtil.writeValue(to)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(service, times(1)).update(to, to.getId());
    }

    @Test
    public void getOnDay() throws Exception {
        List<Lunch> lunches = toList(allLunch());
        LocalDate findDay = LocalDate.of(2018, 1, 1);
        when(service.getAllForDate(findDay)).thenReturn(lunches);

        mockMvc.perform(get(URL + "?date=" + findDay.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(lunches)));

        verify(service, times(1)).getAllForDate(findDay);
    }

    @Test
    public void deleteQuery() throws Exception {
        int id = VOPER.getId();

        mockMvc.perform(delete(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(id);
    }

    @Test
    public void getOne() throws Exception {
        int id = BIG_MACK.getId();
        when(service.get(id)).thenReturn(BIG_MACK);

        mockMvc.perform(get(URL + "/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(JsonUtil.writeValue(BIG_MACK)));

        verify(service, times(1)).get(id);
    }

}