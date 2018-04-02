package org.deveasy.test.core;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;
public final class ResourceHelper {
    private static ObjectMapper objectMapper = new ObjectMapper();


    private ResourceHelper(){
    }


    public static <T> T readJson(String fileName, Class<T> className) throws IOException{
        return objectMapper.readValue(ResourceHelper.class.getClassLoader().getResourceAsStream(fileName), className);
    }


    public static String readJson(String fileName) throws IOException {

        return Resources.toString(ResourceHelper.class.getClassLoader().getResource(fileName), Charsets.UTF_8);
    }

    public static <T> List<T> readJsonAsList(String fileName, Class<T> clazz) throws IOException{
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(ResourceHelper.class.getClassLoader().getResourceAsStream(fileName), type);
    }

}
