package lunchVote.web;

import lunchVote.model.User;
import lunchVote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserRest {

    private final UserService userService;

    @Autowired
    public UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(User user){
        return null;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(){}

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") int id){}

    @GetMapping
    public List<User> getAll(){
        return null;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable("id") int id) {
        return null;
    }

}
