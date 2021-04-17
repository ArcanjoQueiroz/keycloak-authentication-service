package br.com.alexandre.auth;

import com.google.common.base.Strings;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class RestTemplateOAuth2Interceptor implements ClientHttpRequestInterceptor {

  private final Logger logger = LoggerFactory.getLogger(RestTemplateOAuth2Interceptor.class);

  @Override
  public ClientHttpResponse intercept(
      final HttpRequest request, final byte[] body, final ClientHttpRequestExecution execution)
      throws IOException {
    final String accessToken = AuthenticationContext.getAccessToken();
    if (!Strings.isNullOrEmpty(accessToken)) {
      final HttpHeaders headers = request.getHeaders();
      logger.debug("User is logged. Using current access token: '{}'", accessToken);
      headers.set(
          AuthenticationContext.AUTHORIZATION_HEADER, String.format("Bearer %s", accessToken));
    } else {
      logger.debug("There is not an access token in the current context");
    }
    return execution.execute(request, body);
  }
}
