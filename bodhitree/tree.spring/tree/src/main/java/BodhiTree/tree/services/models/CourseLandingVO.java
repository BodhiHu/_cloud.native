package bodhitree.tree.services.models;

import bodhitree.tree.models.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseLandingVO {

    public List<String> categories;
    public List<Course> courses;

    public CourseLandingVO() {
        this(null, null);
    }

    public CourseLandingVO(List<String> categories, List<Course> courses) {
        this.categories = categories != null ? categories : new ArrayList<>();
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public CourseLandingVO addCategory (String cat) {
        this.categories.add(cat);
        return this;
    }
    public CourseLandingVO addCourse (Course course) {
        this.courses.add(course);
        return this;
    }

}
