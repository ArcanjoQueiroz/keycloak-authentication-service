package br.com.alexandre;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.alexandre.client.oauth2.OAuth2KeycloakHttpRequestInterceptor;

public class App {
  private static final String SERVICE_URL = System.getProperty("service_url", "http://localhost:9090/hi");  
  private static final String ACCESS_TOKEN_URI = System.getProperty("access_token_uri", 
      "http://localhost:9999/auth/realms/test/protocol/openid-connect/token");
  private static final String CLIENT_ID = System.getProperty("client_id", "test");  
  private static final String CLIENT_SECRET = System.getProperty("client_secret", 
      "b6f9cf49-6d4b-47d7-803b-dd81072dbcb5");

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static void main(String[] args) {
    final int connectTimeout = 0;
    final int socketTimeout = 120;
    final int connectionRequestTimeout = 60;

    final RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(connectTimeout * 1000)
        .setConnectionRequestTimeout(connectionRequestTimeout * 1000)
        .setSocketTimeout(socketTimeout * 1000)
        .build();

    try (final CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig)
        .addInterceptorFirst(
            new OAuth2KeycloakHttpRequestInterceptor(ACCESS_TOKEN_URI, CLIENT_ID, CLIENT_SECRET))
        .setConnectionTimeToLive(4, TimeUnit.SECONDS)
        .build()) {

      final HttpGet request = new HttpGet(SERVICE_URL);
      try (final CloseableHttpResponse response = client.execute(request)) {
        final StatusLine statusLine = response.getStatusLine();
        final int statusCode = statusLine.getStatusCode();
        final HttpEntity httpEntity = response.getEntity();

        if (httpEntity != null) {
          final String payload = EntityUtils.toString(httpEntity);
          LOGGER.info("Status Code: '{}'\nPayload: '{}'", statusCode, payload);
        } else {
          LOGGER.info("Status Code: '{}'", statusCode);
        }        
      }      

    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
}
