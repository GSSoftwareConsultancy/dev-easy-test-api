/*
 * Copyright 2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deveasy.test.core;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import java.io.IOException;
import java.util.List;

/**
 * Utility for JSON Serialization and De-Serialization
 *
 * @see ObjectMapper
 * @see Resources#toString()
 * @see ObjectMapper#readValue(String, Class)
 *
 * @author Joseph Aruja GS Software Consultancy Ltd
 */
public final class ResourceHelper {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T readJson(String fileName, Class<T> className) throws IOException {
        return objectMapper.readValue(ResourceHelper.class.getClassLoader().getResourceAsStream(fileName), className);
    }

    public static String readJson(String fileName) throws IOException {

        return Resources.toString(ResourceHelper.class.getClassLoader().getResource(fileName), Charsets.UTF_8);
    }

    public static <T> List<T> readJsonAsList(String fileName, Class<T> clazz) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(ResourceHelper.class.getClassLoader().getResourceAsStream(fileName), type);
    }


    public static <T> T readJsonAsObject(String file, Class<T> className) throws IOException {
        return objectMapper.readValue(file, className);
    }


    public static <T> List<T> readJsonAsObjectForList(String file, Class<T> clazz) throws IOException {
        JavaType type = objectMapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return objectMapper.readValue(file, type);
    }


}
