package br.com.alexandre;

import br.com.alexandre.auth.domain.AuthenticationService;
import br.com.alexandre.auth.domain.User;
import br.com.alexandre.auth.swagger.SwaggerProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Hello")
public class HelloController {

  @Autowired private AuthenticationService authenticationService;

  private final Logger logger = LoggerFactory.getLogger(HelloController.class);

  @ApiOperation(
      value = "Hello World",
      produces = MediaType.APPLICATION_JSON_VALUE,
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
  public Map<String, Object> helloWorld() {
    final User user = authenticationService.getUser();
    logger.info("Using azp: '{}', jti: '{}'", user.getAzp(), user.getJti());
    return Map.of("message", "Hello World");
  }

  @ApiOperation(
      value = "Hi",
      produces = MediaType.APPLICATION_JSON_VALUE,
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/hi", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseBody
  public Map<String, Object> hi() {
    final User user = authenticationService.getUser();
    logger.info("Using azp: '{}', jti: '{}'", user.getAzp(), user.getJti());
    return Map.of("message", "Hi");
  }

  @ApiOperation(
      value = "Me",
      produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  @PreAuthorize("hasAnyAuthority('ROLE_USER')")
  public User me() {
    final User user = authenticationService.getUser();
    logger.info("Using azp: '{}', jti: '{}'", user.getAzp(), user.getJti());
    return authenticationService.getUser();
  }
}
