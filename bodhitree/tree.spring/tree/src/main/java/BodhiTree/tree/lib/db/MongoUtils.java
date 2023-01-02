package bodhitree.tree.lib.db;

import bodhitree.tree.AppContext;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoUtils {

    public static MongoCollection getCollection(String collectionName, Class docClass) {
        MongoDatabase db = AppContext.mongoTemplate().getDb();
        return db.getCollection(collectionName, docClass);
    }
}
