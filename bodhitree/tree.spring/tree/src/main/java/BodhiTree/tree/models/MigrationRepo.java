package bodhitree.tree.models;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

public interface MigrationRepo extends CrudRepository<Migration, String> {
    Migration findTopByOrderByVersionDesc();
}
