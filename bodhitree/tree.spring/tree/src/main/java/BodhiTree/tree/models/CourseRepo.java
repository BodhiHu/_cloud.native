package bodhitree.tree.models;

import bodhitree.tree.models.mixins.Mixins;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepo extends PagingAndSortingRepository<Course, String>, CourseRepoCustomized {

    String BEAN_NAME = "courseRepo";

    Page<Course> findByTitleLike(String title, Pageable page);
}

interface CourseRepoCustomized {
    Course create(Course course);
}

class CourseRepoCustomizedImpl implements CourseRepoCustomized {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Course create(Course course) {
        if (course == null) {
            return course;
        }

        Mixins.ts.setTimestampsOnCreation(course);

        return mongoTemplate.save(course);
    }
}

