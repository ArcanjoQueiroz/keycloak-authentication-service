package br.com.alexandre.client.oauth2;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.google.gson.annotations.SerializedName;

class AccessTokenResponse implements Serializable {
  private static final long serialVersionUID = -1714617202662539867L;

  @SerializedName(value = "access_token")
  private String accessToken;

  @SerializedName(value = "expires_in")
  private Long expiresIn;

  @SerializedName(value = "refresh_expires_in")
  private Long refreshExpiresIn;

  @SerializedName(value = "refresh_token")
  private String refreshToken;

  @SerializedName(value = "token_type")
  private String tokenType;

  @SerializedName(value = "not-before-policy")
  private Long notBeforePolicy;

  @SerializedName(value = "session_state")
  private String sessionState;

  @SerializedName(value = "scope")
  private String scope;

  @SerializedName(value = "error")
  private String error;

  @SerializedName(value = "error_description")
  private String errorDescription;

  public String getAccessToken() {
    return accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public Long getExpiresIn() {
    return expiresIn;
  }

  public void setExpiresIn(Long expiresIn) {
    this.expiresIn = expiresIn;
  }

  public Long getRefreshExpiresIn() {
    return refreshExpiresIn;
  }

  public void setRefreshExpiresIn(Long refreshExpiresIn) {
    this.refreshExpiresIn = refreshExpiresIn;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public Long getNotBeforePolicy() {
    return notBeforePolicy;
  }

  public void setNotBeforePolicy(Long notBeforePolicy) {
    this.notBeforePolicy = notBeforePolicy;
  }

  public String getSessionState() {
    return sessionState;
  }

  public void setSessionState(String sessionState) {
    this.sessionState = sessionState;
  }

  public String getScope() {
    return scope;
  }

  public void setScope(String scope) {
    this.scope = scope;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String errorDescription) {
    this.errorDescription = errorDescription;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("accessToken", accessToken)
        .append("expiresIn", expiresIn)
        .append("refreshExpiresIn", refreshExpiresIn)
        .append("refreshToken", refreshToken)
        .append("tokenType", tokenType)
        .append("notBeforePolicy", notBeforePolicy)
        .append("sessionState", sessionState)
        .append("scope", scope)
        .append("error", error)
        .append("errorDescription", errorDescription)
        .build();
  }
    
}
