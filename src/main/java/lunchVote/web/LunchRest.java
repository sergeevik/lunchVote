package lunchVote.web;

import lunchVote.model.Lunch;
import lunchVote.service.LunchService;
import lunchVote.transferObjects.LunchTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping(LunchRest.URL)
public class LunchRest {
    public static final String URL = "/lunches";

    private final LunchService lunchService;

    @Autowired
    public LunchRest(LunchService lunchService) {
        this.lunchService = lunchService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Lunch create(@RequestBody LunchTransfer lunch){
        return lunchService.save(lunch);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody LunchTransfer lunch, @PathVariable("id") int id){
        lunchService.save(lunch);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lunch> getOnDay(@RequestParam(value = "date", required = false) @DateTimeFormat(iso = DATE) LocalDate date){
        if (date == null)
            return lunchService.getAllForDate(LocalDate.now());
        else {
            return lunchService.getAllForDate(date);
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Lunch get(@PathVariable("id") int id){
        return lunchService.get(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        lunchService.delete(id);
    }
}
