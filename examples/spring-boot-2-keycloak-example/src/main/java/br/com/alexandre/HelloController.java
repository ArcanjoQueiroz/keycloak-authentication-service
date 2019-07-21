package br.com.alexandre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.alexandre.auth.domain.AuthenticationService;
import br.com.alexandre.auth.domain.User;
import br.com.alexandre.auth.swagger.SwaggerProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@Api(value = "Hello")
public class HelloController {

  @Autowired
  private AuthenticationService authenticationService;
  
  @ApiOperation(value = "Hello World", produces = MediaType.TEXT_PLAIN_VALUE, authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  //@PreAuthorize("hasAnyAuthority('ROLE_FOO')")
  public String helloWorld() {
    return "Hello World";
  }

  @ApiOperation(value = "Me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME))
  @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  @ResponseBody
  //@PreAuthorize("hasAnyAuthority('ROLE_USER')")
  public User me() {
    return authenticationService.getUser();
  }
}
