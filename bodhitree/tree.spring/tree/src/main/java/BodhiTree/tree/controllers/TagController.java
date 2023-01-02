package bodhitree.tree.controllers;

import bodhitree.tree.lib.HttpReq;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import bodhitree.tree.models.Tag;
import bodhitree.tree.services.TagServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/tags")
public class TagController {

    @Autowired
    TagServices tagServices;

    @PostMapping("/create")
    Result create (@RequestBody Tag tag) {
        return tagServices.create(tag);
    }

    @GetMapping("/{id}")
    Result getTag (@PathVariable(value = "id") String tagId) throws RestException {
        return tagServices.getTag(tagId);
    }

    @GetMapping("/find")
    Result find (
        @RequestHeader HttpHeaders headers,
        @RequestParam(required=false) String search,
        @RequestParam(required=true)  Integer page,
        @RequestParam(required=false) Integer size
    ) throws RestException {
        return tagServices.find(new HttpReq(headers), search, page, size);
    }
}
