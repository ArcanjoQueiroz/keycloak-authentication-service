package br.com.alexandre.client.oauth2;


public class OAuth2KeycloakException extends RuntimeException {

  private static final long serialVersionUID = 2248886760623303815L;

  public OAuth2KeycloakException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public OAuth2KeycloakException(final String message) {
    super(message);
  }

  public OAuth2KeycloakException(final Throwable cause) {
    super(cause);
  }
  
}