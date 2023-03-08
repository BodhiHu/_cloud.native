package citta.bodhitree.auth.controller;

import citta.bodhitree.auth.model.User;
import citta.bodhitree.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @GetMapping("findAll")
    public Flux<User> findAll(){
        return userService.findAll();
    }

    @GetMapping
    public Mono<User> findByUsername(@RequestParam("username") String username){
        return userService.findByUsername(username);
    }

    @PostMapping
    public Mono<User> save(@RequestBody User user){
        return userService.save(user);
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestParam("id") Long id){
        return userService.deleteById(id);
    }
}
