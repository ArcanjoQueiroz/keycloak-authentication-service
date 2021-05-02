package br.com.alexandre.keycloak.spi.base;

import org.keycloak.Config.Scope;

public class EnvVarScopeDecorator implements Scope {

  private final Scope scope;
  
  public EnvVarScopeDecorator(final Scope scope) {
    this.scope = scope;
  }
  
  @Override
  public String get(String key) {
    return this.scope.get(key);
  }

  @Override
  public String get(String key, String defaultValue) {
    return this.scope.get(key, defaultValue);
  }

  @Override
  public String[] getArray(String key) {
    return this.scope.getArray(key);
  }

  @Override
  public Integer getInt(String key) {
    return this.scope.getInt(key);
  }

  @Override
  public Integer getInt(String key, Integer defaultValue) {
    return this.scope.getInt(key, defaultValue);
  }

  @Override
  public Long getLong(String key) {
    return this.scope.getLong(key);
  }

  @Override
  public Long getLong(String key, Long defaultValue) {
    return this.scope.getLong(key, defaultValue);
  }

  @Override
  public Boolean getBoolean(String key) {
    return this.scope.getBoolean(key);
  }

  @Override
  public Boolean getBoolean(String key, Boolean defaultValue) {
    return this.scope.getBoolean(key, defaultValue);
  }

  @Override
  public Scope scope(String... scope) {
    return this.scope.scope(scope);
  }

}
