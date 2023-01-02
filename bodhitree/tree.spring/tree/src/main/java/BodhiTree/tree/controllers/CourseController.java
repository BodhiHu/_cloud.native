package bodhitree.tree.controllers;

import bodhitree.tree.lib.HttpReq;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.models.Course;
import bodhitree.tree.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/courses")
public class CourseController {

    @Autowired
    CourseService courseService;

    @PostMapping("/create")
    Result create(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{id}")
    Result getCourse (@PathVariable(value = "id") String courseId) throws RestException {
        return courseService.getCourse(courseId);
    }

    @GetMapping("/find")
    Result find (
        @RequestHeader HttpHeaders headers,
        @RequestParam(required=false) String search,
        @RequestParam(required=true)  Integer page,
        @RequestParam(required=false) Integer size
    ) throws RestException {
        return courseService.find(new HttpReq(headers), search, page, size);
    }

    @GetMapping("/landingData")
    Result getLandingData (@RequestHeader HttpHeaders headers) {
        return courseService.getLandingData(new HttpReq(headers));
    }

}

