package bodhitree.tree.services;

import bodhitree.tree.models.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepo userRepo;
}

