package citta.bodhitree.auth.service;

import citta.bodhitree.auth.repository.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class UserDetailsService implements ReactiveUserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        return userDetailsRepository.findByUsername(username)
                .cast(UserDetails.class);
    }
}
