package lunchVote.web.security;

import lunchVote.testData.LunchData;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static lunchVote.testData.UserData.USER;
import static lunchVote.web.VoteRest.URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class VoteRestSecurityTest extends SecurityConfig {

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = mockToSecurity();
    }

    @Test
    public void getVoteCounterUser() throws Exception {
        mockMvc.perform(get(URL)
                .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void getVoteCounterFakeAuth() throws Exception {
        mockMvc.perform(get(URL)
                .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Ignore("In service check date and time. From this point can't set LocalDateTime. This test failed after 12:00 with TooLateToVoteException.")
    public void voteUser() throws Exception {
        mockMvc.perform(post(URL+"/"+ LunchData.VOPER.getId())
                        .with(userHttpBasic(USER)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));
    }

    @Test
    public void voteFakeAuth() throws Exception {
        mockMvc.perform(post(URL)
                .with(fakeHttpBasic()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
