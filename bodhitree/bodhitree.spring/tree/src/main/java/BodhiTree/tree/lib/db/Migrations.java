package BodhiTree.tree.lib.db;

import BodhiTree.tree.models.Subject;
import BodhiTree.tree.models.Tag;
import BodhiTree.tree.AppContext;
import BodhiTree.tree.lib.JSON;
import BodhiTree.tree.lib.ResUtils;
import BodhiTree.tree.lib.XML;
import BodhiTree.tree.models.SubjectRepo;
import BodhiTree.tree.models.TagRepo;

import java.util.Arrays;
import java.util.Map;

public class Migrations {

    static void register (Map<Integer, DbMigrate.Migrate> migrations) {
        migrations.put(V1_INSERT_INITIAL_DATA, v1Migrate);
    }

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
    /** V1    *************************************************************************************/

}

