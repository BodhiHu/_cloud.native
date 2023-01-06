package BodhiTree.tree.models;

import BodhiTree.tree.AppContext;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoJsonSchemaCreator;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called ${modelName}Repository

public interface UserRepo extends PagingAndSortingRepository<User, String> {
    User findByEmail(String email);
    User findByMobilePhone(String mobilePhone);
    User findByUsername(String username);
}
