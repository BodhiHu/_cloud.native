package BodhiTree.tree.services.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingCfg {

    public static final int PAGE_DEFAULT_SIZE = 20;
    public static final int PAGE_MAX_SIZE = 100;

    public static Pageable page (Integer page, Integer size, Sort sort) {
        page = page < 0 ? 0 : page;
        size = (size == null || size <= 0 || size > PAGE_MAX_SIZE) ? PAGE_DEFAULT_SIZE : size;

        return PageRequest.of(page, size, sort);
    }

}
