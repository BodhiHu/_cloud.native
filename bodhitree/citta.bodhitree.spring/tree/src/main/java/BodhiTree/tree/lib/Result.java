package BodhiTree.tree.lib;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

@JsonInclude(Include.NON_NULL)
public class Result {

    final static public String SUCCESS = "SUCCESS";
    final static public String ERROR = "ERROR";
    final static public String WARNING = "WARNING";
    final static public String UNAUTHORIZED = "UNAUTHORIZED";
    final static public String ER_DUP_ENTRY = "ER_DUP_ENTRY";
    final static public String ER_BAD_JSON = "ER_BAD_JSON";
    final static public String NOT_FOUND = "NOT_FOUND";
    final static public String WRONG_PWD = "WRONG_PWD";

    public String code;
    public String title;
    public String message;
    public Object data;


    public Result () {

        this(null, null, null, null);
    }
    public Result (String code) {

        this(code, null, null, null);
    }
    public Result (String code, String message) {

        this(code, null, message, null);
    }
    public Result (String code, String title, String message) {

        this(code, title, message, null);
    }
    public Result (Map params) {

        this(
            (String) params.get("code"),
            (String) params.get("title"),
            (String) params.get("message"),
            (String) params.get("data")
        );
    }
    public Result (JsonNode params) {
        this(
            params.get("code").asText(),
            params.get("title").asText(),
            params.get("message").asText(),
            params.get("data").asText()
        );
    }
    public Result (String code, String title, String message, Object data) {

        this.code = code;
        this.title = title;
        this.message = message;
        this.data = JSON.stringify(data);
    }

    public String toJSON() {
        return JSON.stringify(this);
    }

    public static Result reset (Result res, String code, String title, String message, String data) {
        res.code = code;
        res.title = title;
        res.message = message;
        res.data = data;

        return res;
    }
    public static Result reset (Result res, String code) {
        return Result.reset(res, code, null, null, null);
    }

    public Result code (String code) {
        this.code = code;
        return this;
    }
    public Result title (String title) {
        this.title = title;
        return this;
    }
    public Result message (String msg) {
        this.message = msg;
        return this;
    }
    public Result data (Object data) {
        this.data = data;//JSON.stringify(data);
        return this;
    }
}
