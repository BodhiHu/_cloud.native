package BodhiTree.tree.i18n;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

//    final static public String DEFAULT_LOCALE = "zh";
//
//    List<String> LOCALES = Arrays.asList(
//        "zh",
//        "en"
//    );
//
//    @Override
//    public Locale resolveLocale(HttpServletRequest request) {
//        String locale = request.getHeader("Accept-Language");
//
//        if (StringUtils.isEmpty(locale)) {
//            locale = DEFAULT_LOCALE;
//        }
//        locale = locale.replace("-", "_").toLowerCase();
//        if (!LOCALES.contains(locale)) {
//            locale = DEFAULT_LOCALE;
//        }
//
//        String[] parts = locale.split("_");
//        String lang = parts[0], country = "", variant = "";
//        if (parts.length > 1) {
//            country = parts[1].toUpperCase();
//        }
//        if (parts.length > 2) {
//            variant = parts[2];
//        }
//
//        return new Locale(lang, country, variant);
//    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename("messages");
        rs.setDefaultEncoding("UTF-8");
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
