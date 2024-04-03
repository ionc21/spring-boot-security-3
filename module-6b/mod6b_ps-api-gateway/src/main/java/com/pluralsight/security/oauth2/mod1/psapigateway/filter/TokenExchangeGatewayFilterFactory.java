package com.pluralsight.security.oauth2.mod1.psapigateway.filter;

import com.pluralsight.security.oauth2.mod1.psapigateway.dto.TokenExchangeRequest;
import com.pluralsight.security.oauth2.mod1.psapigateway.dto.TokenExchangeResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class TokenExchangeGatewayFilterFactory extends AbstractGatewayFilterFactory<TokenExchangeGatewayFilterFactory.Config> {

    private static final String TOKEN_EXCHANGE_GRANT_TYPE = "urn:ietf:params:oauth:grant-type:token-exchange";
    private static final String REQUEST_TOKEN_TYPE = "urn:ietf:params:oauth:token-type:access_token";
    private static final String PORTFOLIO_SERVICE_CLIENT_ID = "portfolio-service";
    @Value("${portfolio-service.oauth.client-id}")
    private String clientId;
    @Value("${portfolio-service.oauth.secret}")
    private String clientSecret;

    public TokenExchangeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            MultiValueMap<String, String> tokenExchangeRequest = new LinkedMultiValueMap<>();
            tokenExchangeRequest.add("audience", PORTFOLIO_SERVICE_CLIENT_ID);
            tokenExchangeRequest.add("grant_type", TOKEN_EXCHANGE_GRANT_TYPE);
            tokenExchangeRequest.add("requested_token_type", REQUEST_TOKEN_TYPE);
            tokenExchangeRequest.add("subject_token", HttpHeaders.AUTHORIZATION);
            tokenExchangeRequest.add("client_id", clientId);
            tokenExchangeRequest.add("client_secret", clientSecret);


            WebClient webClient = WebClient.builder()
                    .baseUrl("http://localhost:8090").build();
            return webClient.post().uri("/realms/crypto-portfolio/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(tokenExchangeRequest)).exchangeToMono(response -> response.bodyToMono(TokenExchangeResponse.class))
                    .flatMap(response -> {
                        exchange.getResponse().getHeaders().setBearerAuth(response.getAccessToken());
                        return chain.filter(exchange);
                    });
        };
    }

    public static class Config {

    }

}
