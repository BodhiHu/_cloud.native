package bodhitree.tree.services;

import bodhitree.tree.lib.Result;
import bodhitree.tree.models.Subject;
import bodhitree.tree.models.SubjectRepo;
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
