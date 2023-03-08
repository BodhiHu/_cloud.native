package citta.bodhitree.auth.service;

import citta.bodhitree.auth.model.User;
import citta.bodhitree.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder password = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final UserRepository userRepository;

    public Flux<User> findAll () {
        return userRepository.findAll();
    }

    public Mono<User> findByUsername (String username) {
        return userRepository.findByUsername(username);
    }

    public Mono<User> save (User user) {
        user.setPassword(password.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Mono<Void> deleteById (Long id) {
        return userRepository.deleteById(id);
    }
}
