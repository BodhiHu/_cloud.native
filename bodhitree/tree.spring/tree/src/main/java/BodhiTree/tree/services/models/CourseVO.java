package bodhitree.tree.services.models;

import bodhitree.tree.models.Course;

public class CourseVO {
    public Course course;
    public AuthorVO author;

    public CourseVO () { }

    public CourseVO (Course course, AuthorVO author) {
        this.course = course;
        this.author = author;
    }
}
