package br.com.alexandre.client.oauth2;

import java.io.Serializable;

import com.google.common.base.Strings;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
public class OAuth2Info implements Serializable {

  private static final long serialVersionUID = -5362363344992883324L;
  
  private String accessTokenUri;
  private String clientId;
  private String clientSecret;
  
  public boolean isConfigured() {
    return !Strings.isNullOrEmpty(accessTokenUri) 
        && !Strings.isNullOrEmpty(clientId) 
        && !Strings.isNullOrEmpty(clientSecret);
  }
}