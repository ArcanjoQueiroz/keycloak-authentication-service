package br.com.alexandre.keycloak.spi.base;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;

public class EnvVarScopeDecoratorTest {

  @Test
  public void shouldConvertCamelCaseToEnvVariable() {
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("url"))
    .isEqualTo("URL");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("driverClassName"))
    .isEqualTo("DRIVER_CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("driver.Class.Name"))
    .isEqualTo("DRIVER_CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("className"))
    .isEqualTo("CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("ClassName"))
    .isEqualTo("CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("Class.Name"))
    .isEqualTo("CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("driver_Class_Name"))
    .isEqualTo("DRIVER_CLASS_NAME");
    assertThat(EnvVarScopeDecorator.camelCaseToEnvVariable("Driver_Class_Name"))
    .isEqualTo("DRIVER_CLASS_NAME");
  }

}
