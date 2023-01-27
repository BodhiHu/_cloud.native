package BodhiTree.tree.services.models;

import BodhiTree.tree.models.Course;
import BodhiTree.tree.AppContext;
import BodhiTree.tree.models.CourseRepo;
import org.bson.types.ObjectId;

public class CourseDAO {

    public static CourseVO findCourseVOById(String courseId) {

        if (courseId == null || !ObjectId.isValid(courseId)) {
            return null;
        }

        CourseVO courseVO;
        CourseRepo courseRepo = (CourseRepo) AppContext.getBean(CourseRepo.BEAN_NAME);

        Course course = courseRepo.findById(courseId).orElse(null);
        if (course == null) {
            return null;
        }

        courseVO = new CourseVO(course,
            AuthorDAO.findAuthorByUserId(course.getAuthorId())
        );

        return courseVO;
    }

}
