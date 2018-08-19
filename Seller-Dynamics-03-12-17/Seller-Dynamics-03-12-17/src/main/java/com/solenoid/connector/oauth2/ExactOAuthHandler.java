
package com.solenoid.connector.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.solenoid.connector.config.ConnectorProperties;
import com.solenoid.connector.contant.OAuthConstants;
import com.solenoid.connector.oauth2.bean.OAuthToken;
import com.solenoid.connector.oauth2.bean.RefreshAccessTokenResponse;

@Component
public class ExactOAuthHandler {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExactOAuthHandler.class);

    @Autowired
    private ConnectorProperties connectorProperties;

    public OAuthToken refreshAccessToken(String refreshToken) {
        LOGGER.info("Refreshing access token, for RT - " + refreshToken);
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("refresh_token", refreshToken);
        form.add("client_id", this.connectorProperties.getClientId());
        form.add("client_secret", this.connectorProperties.getClientSecret());
        form.add("grant_type", "refresh_token");

        MultiValueMap<String, String> header = new LinkedMultiValueMap<>();
        header.add("content-type", "application/x-www-form-urlencoded");
        RestTemplate restTempalate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(form, header);
        ResponseEntity<RefreshAccessTokenResponse> response = restTempalate.postForEntity(OAuthConstants.TOKEN_URL,
                requestEntity, RefreshAccessTokenResponse.class);
        RefreshAccessTokenResponse body = response.getBody();
        OAuthToken authToken = new OAuthToken();
        authToken.setAccessToken(body.getAccess_token());
        authToken.setRefreshToken(body.getRefresh_token());
        LOGGER.info("Access Token refreshed successfuly, for RT - " + refreshToken);
        LOGGER.info("New AT and RT is - " + authToken);
        return authToken;
    }
}
