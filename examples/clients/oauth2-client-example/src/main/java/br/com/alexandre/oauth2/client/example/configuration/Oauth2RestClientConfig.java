package br.com.alexandre.oauth2.client.example.configuration;

import java.util.Arrays;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Oauth2RestClientConfig {

  @Bean
  @ConfigurationProperties("app.oauth2.client")
  protected ClientCredentialsResourceDetails oauthDetails() {
    return new ClientCredentialsResourceDetails();
  }

  @Bean
  protected RestTemplate restTemplate() {
    final OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(oauthDetails());
    restTemplate.setMessageConverters(Arrays.asList(
        new MappingJackson2HttpMessageConverter(),
        new FormHttpMessageConverter(),
        new StringHttpMessageConverter(),
        new ByteArrayHttpMessageConverter()));
    return restTemplate;
  }

}