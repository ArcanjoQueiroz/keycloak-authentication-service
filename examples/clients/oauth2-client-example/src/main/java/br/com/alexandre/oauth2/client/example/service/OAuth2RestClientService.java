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

  @Value("${app.baseUrl}/hi") private String url;

  private Logger logger = LoggerFactory.getLogger(OAuth2RestClientService.class);

  public MyObject getForObject() {
    logger.info("Sending OAuth2 HTTP Request to: '{}'...", url);
    final MyObject object = restTemplate.getForObject(url, MyObject.class);
    return object;
  }

  public static class MyObject implements Serializable {

    private static final long serialVersionUID = -6910163208050052547L;

    private String message;

    public MyObject() { }

    public MyObject(String message) {
      this.message = message;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((message == null) ? 0 : message.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }
      if (obj == null) {
        return false;
      }
      if (getClass() != obj.getClass()) {
        return false;
      }
      MyObject other = (MyObject) obj;
      if (message == null) {
        if (other.message != null) {
          return false;
        }
      } else if (!message.equals(other.message)) {
        return false;
      }        
      return true;
    }

    @Override
    public String toString() {
      return "MyObject [message=" + message + "]";
    }  

  }

}
