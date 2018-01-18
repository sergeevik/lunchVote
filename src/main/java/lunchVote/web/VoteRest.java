package lunchVote.web;

import lunchVote.AuthUser;
import lunchVote.model.Vote;
import lunchVote.service.VoteService;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(VoteRest.URL)
public class VoteRest {
    public static final String URL = "/vote";

    private final VoteService voteService;

    @Autowired
    public VoteRest(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(value = "/{lunchId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Vote vote(@PathVariable int lunchId){
        return voteService.save(lunchId, AuthUser.getId(), LocalDate.now());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteCounter> getVotingResultsOnDay(@RequestParam(name = "date", required = false)
                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        if (date == null)
            date = LocalDate.now();
        return voteService.getDayResult(date);
    }
}
