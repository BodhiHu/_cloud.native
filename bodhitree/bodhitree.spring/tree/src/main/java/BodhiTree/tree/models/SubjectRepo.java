package BodhiTree.tree.models;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface SubjectRepo extends PagingAndSortingRepository<Subject, String> {
    List<Subject> findByNameLike(String name);
    Subject findByName(String name);
}

