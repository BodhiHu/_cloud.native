package BodhiTree.tree.models;

import org.bson.Document;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.data.repository.PagingAndSortingRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called ${modelName}Repository

public interface UserRepo extends
    PagingAndSortingRepository<User, String>
{

    User findByEmail(String email);
    User findByMobilePhone(String mobilePhone);
    User findByUsername(String username);
}

class UserSchema {

    public static MongoJsonSchema createSchema () {
//        MongoJsonSchema schema = MongoJsonSchemaCreator.create(AppContext.mongoOperations().getConverter())
//            .createSchemaFor(User.class);


//        MongoJsonSchema schema = MongoJsonSchema.builder()
//            .required("createdAt")
//            .build();

        MongoJsonSchema schema = MongoJsonSchema.of(
            Document.parse(jsonSchema())
        );
        return schema;
    }

    static String jsonSchema() {
        return "{ \"TODO\": \"load schema json\" }";
    }
}

