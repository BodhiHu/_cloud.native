package bodhitree.tree;

import bodhitree.tree.lib.HttpUtils;
import bodhitree.tree.lib.RestException;
import bodhitree.tree.lib.Result;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonParseException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
class AppExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest req, HttpServletResponse res, Exception exc) throws Exception {

        RestException restExc = null;
        boolean shouldThrow = false;

        if (exc instanceof DataIntegrityViolationException) {

            /** TODO: Handle DB exceptions */
        }

        if (exc instanceof JsonParseException || exc instanceof JsonProcessingException) {
            restExc = new RestException(
                HttpServletResponse.SC_BAD_REQUEST,
                new Result(
                    Result.ER_BAD_JSON,
                    "Bad JSON",
                    exc.getMessage()
                )
            );
        }

//        if (exc instanceof BadCredentialsException) {
//            restExc = new RestException(
//                HttpServletResponse.SC_UNAUTHORIZED,
//                new Result(
//                    Result.ERROR,
//                    "Bad Credentials",
//                    exc.getMessage()
//                )
//            );
//        }

        if (exc instanceof RestException) {
            restExc = (RestException) exc;
        }

        if (restExc == null) {
            shouldThrow = true;
            restExc = new RestException(
                HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                new Result(
                    Result.ERROR,
                    "Internal Server Error",
                    exc.getMessage()
                )
            );
        }

        HttpUtils.sendJson(res, restExc.httpStatusCode, restExc.result);

        if (shouldThrow) {
            throw exc;
        }

        return null;
    }
}
