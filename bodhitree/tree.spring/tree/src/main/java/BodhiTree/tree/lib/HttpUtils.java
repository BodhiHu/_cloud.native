package bodhitree.tree.lib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HttpUtils {
    static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static HttpServletResponse sendJson(HttpServletResponse servletRes, int statusCode, Object data) {
        servletRes.setStatus(statusCode);
        servletRes.setContentType("application/json;charset=UTF-8");
        try {
            servletRes.getWriter().write(JSON.stringify(data));
            servletRes.getWriter().flush();
            servletRes.getWriter().close();
        } catch (IOException e) {
            logger.error("HttpsUtils.sendJson:");
            e.printStackTrace();
        }

        return servletRes;
    }

    public static Locale getUserLocale (HttpHeaders headers) {
        List<Locale> locales = headers.getAcceptLanguageAsLocales();

        if (locales.size() == 0) {
            return null;
        }

        return locales.get(0);
    }
}

