package BodhiTree.tree.lib;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JSON {

    static Logger logger = LoggerFactory.getLogger(JSON.class);

    private static ObjectMapper mapper;
    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
            mapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            //jsonObjMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        }
        return mapper;
    }

    public static String stringify (Object obj) {
        String ret = null;
        try {
            ret = getMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            logger.error("JSON.stringify:");
            e.printStackTrace();
            ret = "JSON.stringify error: " + e.getMessage();
        }

        return ret;
    }
    public static Object parse (JsonNode jsonNode, Class clazz) throws IOException {
        return parse(jsonNode.toString(), clazz);
    }
    public static Object parse (String json, Class clazz) throws IOException {
        Object ret = null;
        ret = getMapper().readValue(json, clazz);

        return ret;
    }

    public static JsonNode readTree (String json) {
        JsonNode ret = null;
        try {
            ret = getMapper().readTree(json);
        } catch (IOException e) {
            logger.error("JSON.readTree");
            e.printStackTrace();
        }

        return ret;
    }
}
