package BodhiTree.tree.controllers;

import BodhiTree.tree.models.Course;
import BodhiTree.tree.lib.HttpReq;
import BodhiTree.tree.lib.RestException;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.services.CourseService;
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

