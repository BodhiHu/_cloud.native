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

    static Map<Integer, Migrate> migrations = new HashMap<>();

    public static void migrate () throws Exception {

        Migrations.register(migrations);


        int targetVersion = 1;

        try {
            MigrationRepo migrationRepo = (MigrationRepo) AppContext.getBean("migrationRepo");

            Migration latestMigration = migrationRepo.findTopByOrderByVersionDesc();

            int currentVersion = 0;
            if (latestMigration != null) {
                currentVersion = latestMigration.getVersion();
            }

            Assert.isTrue(currentVersion <= targetVersion,
                "currentVersion(" + currentVersion + ") " +
                    "should not be greater than targetVersion(" + targetVersion + ")"
            );

            if (currentVersion == targetVersion) {
                Logs.logger.info("currentVersion is " + currentVersion + ", equals to targetVersion, no migration needed");
                return;
            }

            for (int nextVersion = currentVersion+1; nextVersion <= targetVersion; nextVersion++) {

                Logs.logger.info("running migration for version=" + nextVersion);
                Migrate nextMigrate = migrations.get(nextVersion);
                MigrateState migrateState = nextMigrate.run();

                Assert.isTrue(migrateState == MigrateState.success, "migration error" + nextVersion);

                Logs.logger.info("√ migration success for version=" + nextVersion);
                migrationRepo.save(new Migration(nextVersion, nextMigrate.description));
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
