package BodhiTree.tree.models;

import org.springframework.data.repository.CrudRepository;

public interface MigrationRepo extends CrudRepository<Migration, String> {
    Migration findTopByOrderByVersionDesc();
}
