package bodhitree.tree.lib.auth;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bodhitree.tree.lib.HttpUtils;
import bodhitree.tree.lib.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;

    /* tell client side to begin the authentication flow */
    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {

        Result res = new Result()
            .code(Result.ERROR)
            .message("Unauthorized");

        HttpUtils.sendJson(response, HttpServletResponse.SC_UNAUTHORIZED, res);
    }
}

