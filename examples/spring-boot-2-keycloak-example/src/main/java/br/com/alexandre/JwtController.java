package br.com.alexandre;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import br.com.alexandre.auth.AuthenticationContext;
import br.com.alexandre.auth.swagger.SwaggerProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@Api(value = "JWT") 
public class JwtController {
  
  @Value("${security.oauth2.resource.jwk.key-set-uri}") private String jwksUri;
  
  @ApiOperation(
      value = "Validate JWT",
      produces = MediaType.APPLICATION_JSON_VALUE,
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/jwt", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> jwt() throws IllegalArgumentException, 
      JwkException, MalformedURLException {
    final String token = AuthenticationContext.getAccessToken();

    final DecodedJWT jwt = JWT.decode(token);
    final JwkProvider provider = new UrlJwkProvider(new URL(jwksUri));
    final Jwk jwk = provider.get(jwt.getKeyId());
    final Algorithm algorithm = Algorithm.RSA256(
        (java.security.interfaces.RSAPublicKey) jwk.getPublicKey(), null);
    boolean verified;
    try {
      algorithm.verify(jwt);
      verified = true;
    } catch (final SignatureVerificationException e) {
      verified = false;
    }
    
    return Map.of("jwt", jwt, "verified", verified);
  }
}
