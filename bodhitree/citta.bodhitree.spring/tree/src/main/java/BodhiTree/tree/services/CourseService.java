package BodhiTree.tree.services;

import BodhiTree.tree.models.Course;
import BodhiTree.tree.services.models.CourseDAO;
import BodhiTree.tree.services.models.CourseLandingVO;
import BodhiTree.tree.services.models.CourseVO;
import BodhiTree.tree.services.utils.SvcUtils;
import BodhiTree.tree.lib.HttpReq;
import BodhiTree.tree.lib.RestException;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.models.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class CourseService {

    @Autowired
    CourseRepo courseRepo;

    public Result createCourse (Course course) {
        if (course == null) {
            return null;
        }

        course = courseRepo.create(course);
        return new Result(Result.SUCCESS)
            .data(course.getId());
    }

    public Result getCourse (String courseId) throws RestException {
        CourseVO courseVO = CourseDAO.findCourseVOById(courseId);

        if (courseVO == null) {
            throw new RestException(HttpServletResponse.SC_NOT_FOUND, null);
        }

        return new Result(Result.SUCCESS)
            .data(courseVO);
    }

    public Result find (HttpReq req, String search, Integer page, Integer size)
        throws RestException {

        return SvcUtils.find(
            courseRepo, "findByTitleLike",
            req, search, page, size, Sort.by(DESC, "updatedAt")
        );
    }

    public Result getLandingData (HttpReq req) {
        CourseLandingVO data = new CourseLandingVO(
            // categories
            Arrays.asList("mindfulness", "chinese", "confucian", "english", "math", "nature", "physics", "taoism"),
            // courses
            new ArrayList<>()
        );

        return new Result(Result.SUCCESS)
            .data(data);
    }

}
