package BodhiTree.tree.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class I18N {

    private static ResourceBundleMessageSource msgSource;

    @Autowired
    I18N (ResourceBundleMessageSource msgSource) {
        I18N.msgSource = msgSource;
    }

    public static String getMsg(String code, String... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return msgSource.getMessage(code, args, locale);
    }

    public static  String t (String code, String... args) {
        return I18N.getMsg(code, args);
    }
}

