package br.com.alexandre;

import org.springframework.context.annotation.Configuration;
import br.com.alexandre.auth.swagger.SwaggerSecurityConfig;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig extends SwaggerSecurityConfig {

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("Spring Boot 2 Keycloak Example")
        .description("REST API")
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")                
        .version("1.0")
        .build();
  }

  @Override
  public ApiSelectorBuilder api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any());
  }

}
