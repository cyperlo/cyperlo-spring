package com.cyperlo.common.utils;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JacksonUtil {

    private static ObjectMapper objectMapper;

    JacksonUtil() {
        try {
            ObjectMapper mapper = SpringUtil.getBean(ObjectMapper.class);
            JacksonUtil.objectMapper = mapper;
        } catch (Exception e) {
            log.debug("ObjectMapper bean not found, using default ObjectMapper");
            JacksonUtil.objectMapper = new ObjectMapper();
        }   
    }

    /**
     * 将对象转成 json 字符串
     * @param object 需要转换的对象
     * @return 转换后的 json 字符串
     */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error("toJson error", e);
            throw new RuntimeException(e.getMessage().isEmpty() ? "JSON serialization failed" : e.getMessage(), e);
        }
    }

    /**
     * 将 json 字符串转成对象
     * @param json 需要转换的 json 字符串
     * @param clazz 转换后的对象类型
     * @return 转换后的对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("fromJson error", e);
            throw new RuntimeException(e.getMessage().isEmpty() ? "JSON deserialization failed" : e.getMessage(), e);
        }
    }

    /**
     * 将 json 字符串转成对象列表
     * @param json 需要转换的 json 字符串
     * @param typeReference 转换后的对象类型
     * @return 转换后的对象列表
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (Exception e) {
            log.error("fromJson error", e);
            throw new RuntimeException(e.getMessage().isEmpty() ? "JSON deserialization failed" : e.getMessage(), e);
        }
    }
}
