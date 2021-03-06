package com.datealive.utils;

/**
 * @ClassName: SpringUtils
 * @Description: TODO
 * @author: datealive
 * @date: 2021/3/6  16:05
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring上下文工具类，用以让普通类获取Spring容器中的Bean
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 通过name获取 Bean
     */
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
}
