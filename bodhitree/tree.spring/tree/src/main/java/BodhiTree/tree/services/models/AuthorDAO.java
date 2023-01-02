package bodhitree.tree.services.models;

import bodhitree.tree.lib.db.MongoUtils;
import bodhitree.tree.models.User;
import com.mongodb.client.MongoCollection;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.*;

public class AuthorDAO {

    public static AuthorVO findAuthorByUserId (String userId) {

        if (userId == null || !ObjectId.isValid(userId)) {
            return null;
        }

        MongoCollection<User> usersCollection = MongoUtils.getCollection(User.COLLECTION_NAME, User.class);

        AuthorVO author = new AuthorVO(
            usersCollection
                .find(
                    eq("_id", new ObjectId(userId))
                )
                .projection(
                    fields(include(AuthorVO.mappedUserFields))
                )
                .first()
        );

        if (author.userId == null) {
            author.userId = userId;
        }

        return author;
    }

}
