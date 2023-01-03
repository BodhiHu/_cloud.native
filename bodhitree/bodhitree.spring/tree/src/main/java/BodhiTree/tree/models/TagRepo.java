package BodhiTree.tree.models;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TagRepo extends PagingAndSortingRepository<Tag, String> {
    Tag findByName(String name);
    Page<Tag> findByNameLike(String name, Pageable pageable);
}

