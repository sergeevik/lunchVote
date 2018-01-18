package lunchVote.web;

import lunchVote.model.Restaurant;
import lunchVote.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RestaurantRest.URL)
public class RestaurantRest {

    public static final String URL = "/restaurants";
    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantRest(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurant create(@RequestBody Restaurant restaurant){
        return restaurantService.create(restaurant);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Restaurant restaurant,
                        @PathVariable("id") int id){
        restaurantService.update(restaurant, id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id){
        restaurantService.delete(id);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Restaurant get(@PathVariable("id") int id){
        return restaurantService.get(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Restaurant> getAll(){
        return restaurantService.getAll();
    }
}
