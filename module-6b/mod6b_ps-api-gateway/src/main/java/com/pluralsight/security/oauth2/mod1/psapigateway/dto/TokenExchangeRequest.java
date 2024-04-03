package com.pluralsight.security.oauth2.mod1.psapigateway.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenExchangeRequest {

    private String grantType;
    private String subjectToken;
    private String requestedTokenType;
    private String audience;
    private String clientId;
    private String clientSecret;

    @JsonProperty("grant_type")
    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @JsonProperty("subject_token")
    public String getSubjectToken() {
        return subjectToken;
    }

    public void setSubjectToken(String subjectToken) {
        this.subjectToken = subjectToken;
    }

    @JsonProperty("requested_token_type")
    public String getRequestedTokenType() {
        return requestedTokenType;
    }

    public void setRequestedTokenType(String requestedTokenType) {
        this.requestedTokenType = requestedTokenType;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

}
