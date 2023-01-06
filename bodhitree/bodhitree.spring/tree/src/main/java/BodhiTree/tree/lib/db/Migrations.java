package BodhiTree.tree.lib.db;

import BodhiTree.tree.models.Subject;
import BodhiTree.tree.models.Tag;
import BodhiTree.tree.models.User;
import BodhiTree.tree.AppContext;
import BodhiTree.tree.lib.JSON;
import BodhiTree.tree.lib.ResUtils;
import BodhiTree.tree.lib.XML;
import BodhiTree.tree.models.SubjectRepo;
import BodhiTree.tree.models.TagRepo;
import org.springframework.data.mongodb.core.CollectionOptions;

import java.util.Arrays;

public class Migrations {

    /** V1    *************************************************************************************/
    static final int V1_INSERT_INITIAL_DATA = 1;
    static DbMigrate.Migrate v1Migrate = new DbMigrate.Migrate(V1_INSERT_INITIAL_DATA, "V1_INSERT_INITIAL_DATA") {
        @Override
        public DbMigrate.MigrateState run() throws Exception {

            // subjects
            SubjectRepo subjectRepo = (SubjectRepo) AppContext.getBean("subjectRepo");

            String[] initialSubjectsData = new String[] {
                "initial-data/subjects/buddha-zh_cn.xml",
                "initial-data/subjects/confucius-zh_cn.xml",
                "initial-data/subjects/laotse-zh_cn.xml",
            };

            Subject subject;
            for (String s : initialSubjectsData) {
                subject = XML.mapper().readValue(ResUtils.loadFile(s), Subject.class);
                subjectRepo.save(subject);
            }

            // tags
            TagRepo tagRepo = (TagRepo) AppContext.getBean("tagRepo");

            String tagsDataPath = "initial-data/tags/tags.json";
            Tag[] tags = JSON.getMapper().readValue(ResUtils.loadFile(tagsDataPath), Tag[].class);
            tagRepo.saveAll(Arrays.asList(tags));

            // --

            return DbMigrate.MigrateState.success;
        }
    };

    /** V2    *************************************************************************************/
    static final int V2_CREATE_USER_COLLECTION = 2;
    static DbMigrate.Migrate v2Migrate = new DbMigrate.Migrate(V2_CREATE_USER_COLLECTION, "V2_CREATE_USER_COLLECTION") {
        @Override
        public DbMigrate.MigrateState run() throws Exception {

            // users
            AppContext.mongoTemplate().createCollection(User.class, CollectionOptions.empty().schema(User.jsonSchema()));

            return DbMigrate.MigrateState.success;
        }
    };

    /** Register the migrations here ***************************************************************/
    static DbMigrate.Migrate dbMigrates[] = new DbMigrate.Migrate[]{
            v1Migrate,
            v2Migrate,
    };
}
