package bodhitree.tree.controllers;

import bodhitree.tree.lib.Result;
import bodhitree.tree.models.Subject;
import bodhitree.tree.services.SubjectService;
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

