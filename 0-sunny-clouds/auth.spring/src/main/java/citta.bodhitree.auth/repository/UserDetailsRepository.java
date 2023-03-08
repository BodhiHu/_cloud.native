package citta.bodhitree.auth.repository;

import citta.bodhitree.auth.model.AuthUserDetails;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface UserDetailsRepository extends ReactiveCrudRepository<AuthUserDetails, Long> {

    Mono<AuthUserDetails> findByUsername (String username);

}
