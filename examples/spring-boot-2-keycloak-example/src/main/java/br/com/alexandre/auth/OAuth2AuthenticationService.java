package br.com.alexandre.auth;

import br.com.alexandre.auth.domain.AuthenticationService;
import br.com.alexandre.auth.domain.User;
import br.com.alexandre.auth.domain.UserDetailsService;
import com.google.common.base.Strings;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class OAuth2AuthenticationService extends AbstractAuthenticationService
    implements AuthenticationService {

  private Logger logger = LoggerFactory.getLogger(OAuth2AuthenticationService.class);

  @Autowired private UserDetailsService userDetailsService;

  @Override
  protected User getAuthenticatedUser() {
    final OAuth2AuthenticationDetails authDetails = getAuthenticationDetails();
    if (authDetails.getDecodedDetails() == null) {
      final User decodedUser = decode(authDetails);
      final User userDetails = userDetailsService.getUserDetails(decodedUser);
      authDetails.setDecodedDetails(userDetails);
    }
    return (User) authDetails.getDecodedDetails();
  }

  @SuppressWarnings("unchecked")
  protected User decode(final OAuth2AuthenticationDetails authDetails) {
    logger.debug("Decoding JWT...");
    final String tokenValue = authDetails.getTokenValue();
    if (Strings.isNullOrEmpty(tokenValue)) {
      logger.warn("Empty token value for request");
    }
    final Jwt jwt = JwtHelper.decode(tokenValue);

    final Map<String, Object> claims = JsonParserFactory.create().parseMap(jwt.getClaims());

    final String azp = claims.get("azp") != null ? claims.get("azp").toString() : "";
    final String jti = claims.get("jti") != null ? claims.get("jti").toString() : "";
    final String userName =
        claims.get("preferred_username") != null
            ? claims.get("preferred_username").toString()
            : "unknown";
    final String givenName =
        claims.get("given_name") != null ? claims.get("given_name").toString() : null;
    final String familyName =
        claims.get("family_name") != null ? claims.get("family_name").toString() : null;
    final String email = claims.get("email") != null ? claims.get("email").toString() : null;

    final List<String> roles =
        claims.get("resource_access") != null
            ? ((Map<String, Map<String, List<String>>>) claims.get("resource_access"))
                .entrySet().stream()
                    .filter(e -> e.getKey().equals(azp))
                    .flatMap(y -> y.getValue().values().stream())
                    .flatMap(List::stream)
                    .filter(s -> s.startsWith("ROLE_"))
                    .collect(Collectors.toList())
            : null;

    if (logger.isDebugEnabled()) {
      logger.debug("User: '{}'. Claims: '{}'", userName, claims);
    } else {
      logger.info("User: '{}'", userName);
    }

    final User user = new User(null, userName, givenName, familyName, email, roles);
    user.setJti(jti);

    return user;
  }

  private OAuth2AuthenticationDetails getAuthenticationDetails() {
    return (OAuth2AuthenticationDetails)
        SecurityContextHolder.getContext().getAuthentication().getDetails();
  }
}
