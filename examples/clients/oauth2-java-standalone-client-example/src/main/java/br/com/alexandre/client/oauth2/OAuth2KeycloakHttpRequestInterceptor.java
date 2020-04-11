package br.com.alexandre.client.oauth2;

import java.io.IOException;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpException;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class OAuth2KeycloakHttpRequestInterceptor implements HttpRequestInterceptor {

  private String accessTokenUri;
  private String clientId;
  private String clientSecret;
  private HttpClient httpClient;
  private Gson gson;

  private Logger logger = LoggerFactory.getLogger(OAuth2KeycloakHttpRequestInterceptor.class);

  public OAuth2KeycloakHttpRequestInterceptor(final String accessTokenUri, final String clientId, 
      final String clientSecret) {
    this.accessTokenUri = accessTokenUri;
    this.clientId = clientId;
    this.clientSecret = clientSecret;
    this.httpClient = HttpClient.newBuilder()
        .version(HttpClient.Version.HTTP_1_1)
        .build();
    this.gson = new GsonBuilder().create();
  }

  @Override
  public void process(org.apache.http.HttpRequest request, HttpContext context) 
      throws HttpException, IOException {
    request.addHeader("Authorization", String.format("Bearer %s", getAccessToken()));      
  }

  protected String getAccessToken() throws IOException {
    final Map<Object, Object> data = new HashMap<>();
    data.put("client_id", clientId);
    data.put("client_secret", clientSecret);
    data.put("grant_type", "client_credentials");

    final HttpRequest request = HttpRequest.newBuilder()
        .POST(ofFormData(data))
        .uri(URI.create(accessTokenUri))
        .header("Content-Type", "application/x-www-form-urlencoded")
        .header("Cache-Control", "no-cache")            
        .build();

    logger.debug("Sending authorization request to: '{} using data: '{}'", accessTokenUri, data);
    HttpResponse<String> response;
    try {
      response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    } catch (final InterruptedException e) {
      throw new OAuth2KeycloakException(e);
    }

    final AccessTokenResponse accessTokenResponse = gson.fromJson(response.body(), 
        AccessTokenResponse.class);
    logger.debug("Response received: '{}'", accessTokenResponse);

    if (!Strings.isNullOrEmpty(accessTokenResponse.getError())) {
      throw new OAuth2KeycloakException(String.format("Error: %s, Description: %s", 
          accessTokenResponse.getError(), accessTokenResponse.getErrorDescription()));
    }

    final String accessToken = accessTokenResponse.getAccessToken();
    if (Strings.isNullOrEmpty(accessToken)) {
      throw new OAuth2KeycloakException("Access token is null");
    }
    return accessToken;
  }

  private HttpRequest.BodyPublisher ofFormData(Map<Object, Object> data) {
    var builder = new StringBuilder();
    for (Map.Entry<Object, Object> entry : data.entrySet()) {
      if (builder.length() > 0) {
        builder.append("&");
      }
      builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
      builder.append("=");
      builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
    }
    return HttpRequest.BodyPublishers.ofString(builder.toString());
  }

}