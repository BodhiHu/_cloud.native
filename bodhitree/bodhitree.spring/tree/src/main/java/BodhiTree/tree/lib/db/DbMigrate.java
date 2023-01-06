package BodhiTree.tree.lib.db;

import BodhiTree.tree.models.MigrationRepo;
import BodhiTree.tree.AppContext;
import BodhiTree.tree.lib.Logs;
import BodhiTree.tree.models.Migration;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class DbMigrate {

    enum MigrateState {
        success, error
    }

    public static void migrate() throws Exception {

        try {
            MigrationRepo migrationRepo = (MigrationRepo) AppContext.getBean("migrationRepo");

            Migration latestMigration = migrationRepo.findTopByOrderByVersionDesc();

            final int currentVersion = latestMigration != null ? latestMigration.getVersion() : 0;

            for (Migrate migrate : Migrations.dbMigrates) {
                if (migrate.version > currentVersion) {
                    MigrateState migrateState = migrate.run();

                    Assert.isTrue(migrateState == MigrateState.success, "migration error" + migrate.version);

                    Logs.logger.info("√ migration success for version = " + migrate.version);
                    migrationRepo.save(new Migration(migrate.version, migrate.description));
                }
            }
        } catch (Exception exc) {
            Logs.logger.error(exc.getMessage(), exc);
            throw exc;
        }
    }

    abstract static class Migrate {
        public int version;
        public String description;
        public abstract MigrateState run() throws Exception;

        public Migrate (int version, String description) {
            this.version = version;
            this.description = description;
        }
    }
}
