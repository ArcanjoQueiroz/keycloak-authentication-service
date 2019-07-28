package br.com.alexandre.auth.swagger;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ImplicitGrant;
import springfox.documentation.service.LoginEndpoint;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
public abstract class SwaggerSecurityConfig {

  @Value("${security.oauth2.swagger.client-id}")
  private String clientId;

  @Value("${security.oauth2.swagger.realm}")
  private String realm;

  @Value("${security.oauth2.swagger.auth-server-uri}")
  private String oauthServerUri;

  @Bean
  public SecurityScheme oauth() {
    return new OAuthBuilder().name("OAuth2").scopes(scopes()).grantTypes(grantTypes()).build();
  }

  private List<AuthorizationScope> scopes() {
    return Arrays.asList(
        new AuthorizationScope("openid", null),
        new AuthorizationScope("profile", null),
        new AuthorizationScope("email", null));
  }

  @Bean
  public List<GrantType> grantTypes() {
    return Arrays.asList(new ImplicitGrant(new LoginEndpoint(oauthServerUri), "access_token"));
  }

  @Bean
  public SecurityConfiguration securityInfo() {
    final Map<String, Object> additionalQueryParams = new HashMap<>();
    additionalQueryParams.put("nonce", SwaggerProperty.NONCE);

    return SecurityConfigurationBuilder.builder()
        .realm(realm)
        .clientId(clientId)
        .additionalQueryStringParams(additionalQueryParams)
        .build();
  }

  public abstract ApiSelectorBuilder api();

  @Bean
  public Docket docket() {
    return api().build().securitySchemes(Collections.singletonList(oauth()));
  }
}
