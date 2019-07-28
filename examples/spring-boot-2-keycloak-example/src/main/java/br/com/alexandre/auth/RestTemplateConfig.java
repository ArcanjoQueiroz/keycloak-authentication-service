package br.com.alexandre.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig extends AbstractRestTemplateConfig {

  private Logger logger = LoggerFactory.getLogger(RestTemplateConfig.class);

  @Bean
  @ConditionalOnBean(value = MockAuthenticationService.class)
  public RestTemplate getSimpleRestTemplate(
      final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
      final RestTemplateBuilder restTemplateBuilder) {
    logger.info("Building Simple Rest Template...");
    return build(mappingJackson2HttpMessageConverter, restTemplateBuilder).build();
  }

  @Bean
  @ConditionalOnBean(value = OAuth2AuthenticationService.class)
  public RestTemplate getOAuth2RestTemplate(
      final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
      final RestTemplateBuilder restTemplateBuilder) {
    logger.info("Building OAuth2 Rest Template...");
    return build(mappingJackson2HttpMessageConverter, restTemplateBuilder)
        .interceptors(new RestTemplateOAuth2Interceptor())
        .build();
  }
}
