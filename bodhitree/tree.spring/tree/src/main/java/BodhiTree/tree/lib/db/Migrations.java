package bodhitree.tree.lib.db;

import bodhitree.tree.AppContext;
import bodhitree.tree.lib.JSON;
import bodhitree.tree.lib.ResUtils;
import bodhitree.tree.lib.XML;
import bodhitree.tree.models.Subject;
import bodhitree.tree.models.SubjectRepo;
import bodhitree.tree.models.Tag;
import bodhitree.tree.models.TagRepo;

import java.util.Arrays;
import java.util.Map;

import static bodhitree.tree.lib.db.DbMigrate.Migrate;
import static bodhitree.tree.lib.db.DbMigrate.MigrateState;

public class Migrations {

    static void register (Map<Integer, Migrate> migrations) {
        migrations.put(V1_INSERT_INITIAL_DATA, v1Migrate);
    }

    /** V1    *************************************************************************************/
    static final int V1_INSERT_INITIAL_DATA = 1;
    static Migrate v1Migrate = new Migrate(V1_INSERT_INITIAL_DATA, "V1_INSERT_INITIAL_DATA") {
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

            return MigrateState.success;
        }
    };
    /** V1    *************************************************************************************/

}

