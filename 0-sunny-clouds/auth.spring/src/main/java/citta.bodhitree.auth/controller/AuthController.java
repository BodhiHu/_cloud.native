package citta.bodhitree.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import citta.bodhitree.auth.model.HttpResult;
import citta.bodhitree.auth.model.LoginResponse;
import citta.bodhitree.auth.model.User;
import citta.bodhitree.auth.repository.UserDetailsRepository;
import citta.bodhitree.auth.service.JwtSigner;
import citta.bodhitree.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
@Slf4j
public class AuthController {

    private final UserDetailsRepository myUserRepository;
    private final UserService userService;
    private final JwtSigner jwtSigner;
    private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @PostMapping("login")
    public Mono<HttpResult> login (@RequestBody Map<String, String> user) {

        ObjectMapper mapper = new ObjectMapper();

        return Mono.just(user.get("username"))
                .flatMap(myUserRepository::findByUsername)
                .doOnNext(i -> log.info("{}", i))
                .filter(it -> password.matches(user.get("password"), it.getPassword()))
                .map(it -> {
                    try {
                        return new HttpResult(HttpStatus.OK.value(),
                                "Login success",
                                new LoginResponse(it.getUsername(),
                                        mapper.writeValueAsString(it
                                                .getAuthorities()
                                                .stream()
                                                .map(GrantedAuthority::getAuthority)
                                                .collect(Collectors.toList())),
                                        jwtSigner.generateToken(it)));
                    } catch (JsonProcessingException e) {
                        return new HttpResult();
                    }
                })
                .onErrorResume(e -> Mono.empty())
                .switchIfEmpty(Mono.just(new HttpResult(HttpStatus.UNAUTHORIZED.value(), "Login failed", null)));
    }

//    @CrossOrigin
    @PostMapping("signup")
    public Mono<HttpResult> signUp (@RequestBody User user) {

        return Mono.just(user)
                .map(userService::save)
                .map(it -> new HttpResult(HttpStatus.OK.value(), "Register success", null))
                .onErrorResume(e -> Mono.just(new HttpResult(HttpStatus.UNAUTHORIZED.value(), "Register error", e)));
    }
}
