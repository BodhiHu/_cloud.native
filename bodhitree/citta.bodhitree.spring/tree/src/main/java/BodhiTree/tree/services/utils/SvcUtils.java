package BodhiTree.tree.services.utils;

import BodhiTree.tree.services.config.PagingCfg;
import BodhiTree.tree.lib.HttpReq;
import BodhiTree.tree.lib.Logs;
import BodhiTree.tree.lib.RestException;
import BodhiTree.tree.lib.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.util.ClassUtils;

public class SvcUtils {


    public static <T> Result find (
        PagingAndSortingRepository repo, String findByMethodName,
        HttpReq req, String search, Integer page, Integer size, Sort sort
    ) throws RestException {

        Page<T> data = null;
        Pageable pageReq = PagingCfg.page(page, size, sort);

        try {
            if (search == null || search.length() == 0) {
                data = repo.findAll(pageReq);
            } else {
                data = (Page<T>) ClassUtils.getMethod(repo.getClass(), findByMethodName)
                    .invoke(repo, search, pageReq);
            }
        } catch (Exception exc) {
            Logs.logger.error(exc.getMessage(), exc);
            throw RestException.internalServerError();
        }

        return new Result()
            .code(Result.SUCCESS)
            .data(data);
    }

}

