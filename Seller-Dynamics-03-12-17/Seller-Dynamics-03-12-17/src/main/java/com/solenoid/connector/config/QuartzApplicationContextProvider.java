
package com.solenoid.connector.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    @SuppressWarnings("static-access")
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;

    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}
