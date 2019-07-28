package br.com.alexandre.auth;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

public abstract class AbstractRestTemplateConfig {

  private Logger logger = LoggerFactory.getLogger(AbstractRestTemplateConfig.class);

  @Value("${app.connect.timeout:50000}")
  private int connectTimeout;

  @Value("${rest.read.timeout:300000}")
  private int readTimeout;

  protected RestTemplateBuilder build(
      final MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter,
      final RestTemplateBuilder restTemplateBuilder) {
    logger.info("Using connect timeout: {} ms, read timeout: {} ms", connectTimeout, readTimeout);
    return restTemplateBuilder
        .setConnectTimeout(Duration.ofMillis(connectTimeout))
        .setReadTimeout(Duration.ofMillis(readTimeout))
        .messageConverters(
            mappingJackson2HttpMessageConverter,
            new StringHttpMessageConverter(),
            new FormHttpMessageConverter(),
            new ResourceHttpMessageConverter());
  }
}
