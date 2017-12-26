package lunchVote.web;

import lunchVote.model.Lunch;
import lunchVote.service.LunchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lunches")
public class LunchRest {

    @Autowired
    LunchService lunchService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Lunch create(Lunch lunch){
        return null;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Lunch lunch, @PathVariable("id") int id){
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lunch> getOnDay(@RequestParam("date") String date){
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){}
}
