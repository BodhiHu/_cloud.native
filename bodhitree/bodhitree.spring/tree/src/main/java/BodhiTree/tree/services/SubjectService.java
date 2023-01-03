package BodhiTree.tree.services;

import BodhiTree.tree.models.Subject;
import BodhiTree.tree.lib.Result;
import BodhiTree.tree.models.SubjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    SubjectRepo subjectRepo;

    public Result createSubject (Subject subject) {
        if (subject == null) {
            return null;
        }

        subject = subjectRepo.save(subject);

        return new Result(Result.SUCCESS)
            .data(subject.getId());
    }

    public Result find (String name) {
        List<Subject> subjects = subjectRepo.findByNameLike(name);

        return new Result(Result.SUCCESS)
            .data(subjects);
    }

}
