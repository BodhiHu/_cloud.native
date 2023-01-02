package bodhitree.tree.services;

import bodhitree.tree.lib.HttpReq;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.models.Tag;
import bodhitree.tree.models.TagRepo;
import bodhitree.tree.services.utils.SvcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
public class TagServices {

    @Autowired
    TagRepo tagRepo;

    public Result create (Tag tag) {
        tag = tagRepo.save(tag);
        return new Result(Result.SUCCESS)
            .data(tag.getId());
    }

    public Result getTag (String tagId) throws RestException {
        Tag tag = tagRepo.findById(tagId).orElse(null);

        if (tag == null) {
            throw new RestException(HttpServletResponse.SC_NOT_FOUND, null);
        }

        return new Result(Result.SUCCESS)
            .data(tag);
    }

    public Result find (HttpReq req, String search, Integer page, Integer size)
        throws RestException {

        return SvcUtils.find(
            tagRepo, "findByNameLike",
            req, search, page, size, Sort.by(DESC, "name")
        );
    }
}

