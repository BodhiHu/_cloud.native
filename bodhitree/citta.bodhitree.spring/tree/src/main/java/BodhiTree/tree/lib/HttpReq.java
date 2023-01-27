package BodhiTree.tree.lib;

import org.springframework.http.HttpHeaders;

import java.util.Locale;

public class HttpReq {

    public HttpReq (HttpHeaders headers) {
        this.headers = headers;
    }

    public HttpHeaders headers;
    public Locale getLocale () {
        return HttpUtils.getUserLocale(this.headers);
    }

}
