package net.scriptgate.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object object) throws IOException {
        return mapper.writeValueAsString(object);
    }
}
