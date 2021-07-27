package com.project.inventory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class BeanUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    public static <T> T getBean ( Class<T> getClazz ) {
        return BeanUtils.applicationContext.getBean ( getClazz );
    }

    @Override
    public void setApplicationContext ( ApplicationContext applicationContext ) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
    }
}
