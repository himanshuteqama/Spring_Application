/*
 * Copyright (C) 2017 Solenoid Augment Technologies Limited.
 * All rights reserved.
 */

package com.solenoid.connector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solenoid.connector.config.ConnectorProperties;
import com.solenoid.connector.contant.OAuthConstants;
import com.solenoid.connector.mapper.JSONMapper;
import com.solenoid.connector.oauth2.ExactOAuth2RestTemplate;

/**
 * OAuth Controller for EXACT API authorization.
 */
@Configuration
@EnableOAuth2Client
// @RestController
// @RequestMapping("/oauth")
// @Order(-1000)
public class OAuthConfiguration {

    @Autowired
    private OAuth2RestOperations restTemplate;

    @PostConstruct
    void setup() {
        List<HttpMessageConverter<?>> messageConvertersTemp = new ArrayList<HttpMessageConverter<?>>();

        Jaxb2RootElementHttpMessageConverter jaxbMessageConverter = new Jaxb2RootElementHttpMessageConverter();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_XML);
        mediaTypes.add(MediaType.TEXT_XML);
        jaxbMessageConverter.setSupportedMediaTypes(mediaTypes);

        messageConvertersTemp.add(new JSONMapper(new ObjectMapper()));
        messageConvertersTemp.add(jaxbMessageConverter);
        // messageConvertersTemp.add(new
        // ExactFormOAuth2AccessTokenMessageConverter());
        ((RestTemplate) restTemplate).setMessageConverters(messageConvertersTemp);
    }

    @Bean
    @Scope(scopeName = "singleton")
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oauth2ClientContext,
            ConnectorProperties connectionProperties) {
        /*
         * OAuth2RestTemplate o = new OAuth2RestTemplate(resource(),
         * oauth2ClientContext); o.setAccessTokenProvider(new
         * ExactAccessTokenProvider());
         */
        ExactOAuth2RestTemplate restTemplate = new ExactOAuth2RestTemplate(resource(connectionProperties),
                oauth2ClientContext);
        // restTemplate.setRequestFactory(new
        // RibbonClientHttpRequestFactory(clientFactory));
        return restTemplate;
    }

    @Bean
    protected OAuth2ProtectedResourceDetails resource(ConnectorProperties connectionProperties) {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setAccessTokenUri(OAuthConstants.TOKEN_URL);
        resource.setUserAuthorizationUri(OAuthConstants.AUTHORIZE_URL);
        resource.setClientId(connectionProperties.getClientId());
        // resource.setClientId("{5bc13a3c-537b-4002-8b77-4a05867bb057}");
        // resource.setClientSecret("r3EL71CF84Vo");
        resource.setClientSecret(connectionProperties.getClientSecret());
        resource.setGrantType("authorization_code");
        // resource.setClientAuthenticationScheme(AuthenticationScheme.form);
        // resource.set
        return resource;
    }

    @Bean
    @Qualifier("exactRestTemplate")
    public RestTemplate getRestemplate() {
        return new RestTemplate();
    }
}
