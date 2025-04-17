package com.cyperlo.web.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class SpringHelper implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringHelper.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        SpringHelper.environment = environment;
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }
    
    public static <T> T getProperty(String key, Class<T> clazz) {
        return environment.getProperty(key, clazz);
    }

    public static <T> T getProperty(String key, Class<T> clazz, T defaultValue) {
        return environment.getProperty(key, clazz, defaultValue);
    }
}
