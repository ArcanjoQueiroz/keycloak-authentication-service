package br.com.alexandre.oauth2.client.example.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alexandre.oauth2.client.example.configuration.SwaggerProperty;
import br.com.alexandre.oauth2.client.example.service.OAuth2RestClientService;
import br.com.alexandre.oauth2.client.example.service.OAuth2RestClientService.MyObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@Api(value = "Consume")
public class Oauth2RestClientController {

  @Autowired private OAuth2RestClientService service;

  @ApiOperation(
      value = "Consume",
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME),
      produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(value = "/consume", produces = { MediaType.APPLICATION_JSON_VALUE })
  public MyObject consume() {
    return service.getForObject();
  }

  @ApiOperation(
      value = "Hi",
      authorizations = @Authorization(value = SwaggerProperty.AUTH_NAME),
      produces = MediaType.APPLICATION_JSON_VALUE)
  @GetMapping(value = "/hi", produces = { MediaType.APPLICATION_JSON_VALUE })
  public Map<String, Object> hi() {
    return Map.of("message", "Hi!");
  }
}
