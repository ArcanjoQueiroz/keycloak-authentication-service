package br.com.alexandre.client.oauth2;

import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.google.gson.annotations.SerializedName;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(of = { "accessToken" })
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
