package lunchVote.web;

import lunchVote.AuthUser;
import lunchVote.model.Vote;
import lunchVote.service.VoteService;
import lunchVote.testData.UserData;
import lunchVote.testData.VoteData;
import lunchVote.web.json.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collection;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestTest extends MvcConfig{
    private static final String URL = VoteRest.URL;

    private VoteRest rest;
    private VoteService service;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        service = mock(VoteService.class);
        rest = new VoteRest(service);
        mockMvc = super.mockMvcWithController(rest);
    }

    @Test
    public void vote() throws Exception {
        Vote save = new Vote(VoteData.USER_VOTE);
        when(service.save(save.getLunchId(), save.getUserId(), save.getDate())).thenReturn(VoteData.USER_VOTE);
        Authentication old = SecurityContextHolder.getContext().getAuthentication();
        SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken(new AuthUser(UserData.USER), null));

        mockMvc.perform(post(URL + "/" + save.getLunchId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(content().json(JsonUtil.writeValue(VoteData.USER_VOTE)));

        verify(service, times(1)).save(save.getLunchId(), save.getUserId(), save.getDate());
        SecurityContextHolder.getContext().setAuthentication(old);
    }

    @Test
    public void getVotingResultsOnDayToday() throws Exception {
        mockMvc.perform(get(URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(service, times(1)).getDayResult(LocalDate.now());
    }

    @Test
    public void getVotingResultsOnDayTomorrow() throws Exception {
        LocalDate date = LocalDate.now().plusDays(1);
        mockMvc.perform(get(URL + "?date=" + date.toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        verify(service, times(1)).getDayResult(date);
    }

}