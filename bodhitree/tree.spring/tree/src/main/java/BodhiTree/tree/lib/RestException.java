package bodhitree.tree.lib;

import javax.servlet.http.HttpServletResponse;

public class RestException extends Exception {
    public int httpStatusCode;
    public Result result;

    public static RestException internalServerError () {
        return new RestException(
            HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
            new Result("INTERNAL_SERVER_ERROR")
        );
    }

    public RestException(Result result) {
        this(HttpServletResponse.SC_BAD_REQUEST, result);
    }

    public RestException(int httpStatusCode, Result result) {
        this.httpStatusCode = httpStatusCode;
        this.result = result;
    }
}
