package com.solenoid.connector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

import javax.servlet.annotation.WebListener;

@Configuration
@WebListener
public class ConnectorRequestContextListener extends RequestContextListener {
}
