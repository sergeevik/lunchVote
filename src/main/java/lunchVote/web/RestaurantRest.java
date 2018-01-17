package lunchVote.web;

import lunchVote.model.Restaurant;
import lunchVote.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantRest {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantRest(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant create(@RequestBody Restaurant restaurant){
        return null;
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant,
                        @PathVariable("id") int id){

    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){}

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        return null;
    }
}
