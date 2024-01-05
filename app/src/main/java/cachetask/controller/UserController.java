package cachetask.controller;

import cachetask.entity.User;
import cachetask.sevices.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public boolean create(@RequestBody @Valid User user){
        return userService.create(user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean delete(@PathVariable @Valid Long id){
        return userService.delete(id);
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable @Valid Long id, @RequestBody @Valid User user){
       userService.update(user, id);
    }


    @GetMapping
    public List<User> readAll(){
        return  userService.readAll(0,0);
    }
}

