package BodhiTree.tree.controllers;

import BodhiTree.tree.models.Subject;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path= "/subjects")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping("/create")
    Result create(@RequestBody Subject subject) {
        return subjectService.createSubject(subject);
    }

    @GetMapping("/find")
    Result find (@RequestParam(required=false) String name) {
        return subjectService.find(name);
    }

}

