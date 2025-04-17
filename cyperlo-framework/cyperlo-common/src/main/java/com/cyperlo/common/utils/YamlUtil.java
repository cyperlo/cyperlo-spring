package com.cyperlo.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * @author chenhailong
 */
public class YamlUtil {
        private static final ObjectMapper YAML_MAPPER = new ObjectMapper(
                        new YAMLFactory()
                                        .configure(YAMLGenerator.Feature.MINIMIZE_QUOTES, true)
                                        .configure(YAMLGenerator.Feature.WRITE_DOC_START_MARKER, false)
                                        .configure(YAMLGenerator.Feature.INDENT_ARRAYS, true))
                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 获取 ValidatorFactory 和 Validator
        private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        private static final Validator validator = factory.getValidator();

        /**
         * @param object object
         * @return {@link String }
         * @throws JsonProcessingException 序列化异常
         */
        public static <T> String toYaml(T object) throws JsonProcessingException {
                return YAML_MAPPER.writeValueAsString(object);
        }

        /**
         * @param yaml  Yaml 字符串
         * @param clazz 实体类
         * @return {@link T }
         * @throws JsonProcessingException jackson异常
         */
        public static <T> T fromYaml(String yaml, Class<T> clazz) throws JsonProcessingException {
                return YAML_MAPPER.readValue(yaml, clazz);
        }

        public static <T> T fromYaml(InputStream inputStream, Class<T> clazz) throws IOException {
                return YAML_MAPPER.readValue(inputStream, clazz);
        }

        /**
         * @param yaml     yaml
         * @param tClass   class
         * @param validate true => 校验；
         * @return {@link T }
         * @throws RuntimeException
         * @throws JsonProcessingException
         */
        public static <T> T fromYaml(String yaml, Class<T> tClass, boolean validate)
                        throws RuntimeException, JsonProcessingException {
                if (StrUtil.isEmptyIfStr(yaml)) {
                        throw new RuntimeException("yaml is empty");
                }
                T t = fromYaml(yaml, tClass);
                if (validate) {
                        validate(t);
                }
                return t;
        }

        /**
         * @param inputStream 输入流
         * @param clazz 转换类
         * @param validation 是否校验
         * @return {@link T }
         * @throws RuntimeException 运行时异常
         */
        public static <T> T fromYaml(InputStream inputStream, Class<T> clazz, boolean validation) throws RuntimeException, IOException {
                if (inputStream == null) {
                        throw new RuntimeException("input stream is null");
                }
                T t = fromYaml(inputStream, clazz);
                if (validation) {
                        validate(t);
                }
                return t;
        }

        /**
         * @param obj 需要校验的对象
         * @return {@link Boolean }
         * @throws RuntimeException 运行异常
         */
        private static <T> Boolean validate(T obj) throws RuntimeException {
                Set<ConstraintViolation<T>> violations = validator.validate(obj);
                if (!violations.isEmpty()) {
                        StringBuilder errorMessage = new StringBuilder();
                        for (ConstraintViolation<T> violation : violations) {
                                errorMessage.append(violation.getMessage()).append("\n");
                        }
                        throw new RuntimeException("对象校验失败: " + errorMessage.toString());
                }
                return true;
        }
}
