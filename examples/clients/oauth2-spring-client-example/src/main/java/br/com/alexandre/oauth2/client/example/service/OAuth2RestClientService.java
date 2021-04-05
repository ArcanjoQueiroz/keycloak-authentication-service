package br.com.alexandre.oauth2.client.example.service;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OAuth2RestClientService {

  @Autowired private RestTemplate restTemplate;

  @Value("${service.base-url}/hi") private String url;

  private Logger logger = LoggerFactory.getLogger(OAuth2RestClientService.class);

  public MyObject getForObject() {
    logger.info("Sending OAuth2 HTTP Request to: '{}'...", url);
    final MyObject object = restTemplate.getForObject(url, MyObject.class);
    return object;
  }

  @lombok.Getter
  @lombok.Setter
  @lombok.NoArgsConstructor
  @lombok.AllArgsConstructor
  @lombok.EqualsAndHashCode
  @lombok.Builder
  public static class MyObject implements Serializable {

    private static final long serialVersionUID = -6910163208050052547L;

    private String message;
  }

}
