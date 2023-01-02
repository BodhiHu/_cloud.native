package bodhitree.tree.models;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.PagingAndSortingRepository;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

import static bodhitree.tree.models.Token.DEFAULT_LIFESPAN_SECONDS;

// This will be AUTO IMPLEMENTED by Spring into a Bean called ${modelName}Repository

public interface TokenRepo extends PagingAndSortingRepository<Token, String>, TokenRepoCustomized {
    Token findByEmail(String email);
    Token findByMobilePhone(String mobilePhone);
}

interface TokenRepoCustomized {
   void upsertByEmail(String email, String data);
   void upsertByMobilePhone(String mobilePhone, String data);
}

class TokenRepoCustomizedImpl implements TokenRepoCustomized {

    @Autowired
    MongoTemplate mongoTemplate;

    public void upsertByEmail(String email, String data) {
        this.upsert("email", email, data);
    }
    public void upsertByMobilePhone(String mobilePhone, String data) {
        this.upsert("mobilePhone", mobilePhone, data);
    }


    public void upsert(String keyName, String keyValue, String data) {
        this.upsert(keyName, keyValue, data, DEFAULT_LIFESPAN_SECONDS);
    }
    public void upsert(String keyName, String keyValue, String data, int lifespan) {
        mongoTemplate.upsert(
            query(where(keyName).is(keyValue)),
            new Update()
                .setOnInsert("createdAt", new Date())
                .set("data", data)
                .set("lifespan", lifespan)
                .set("updatedAt", new Date()),
            Token.class
        );

    }
}


