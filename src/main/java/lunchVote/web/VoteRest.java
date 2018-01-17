package lunchVote.web;

import lunchVote.model.Vote;
import lunchVote.service.VoteService;
import lunchVote.transferObjects.VoteCounter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/votes")
public class VoteRest {

    private final VoteService voteService;

    @Autowired
    public VoteRest(VoteService voteService) {
        this.voteService = voteService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote create(@RequestBody Vote vote){
        return null;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(){}

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteCounter> getVotingResultsOnDay(){
        return null;
    }
}
