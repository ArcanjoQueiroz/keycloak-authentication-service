package br.com.alexandre.keycloak.spi.base;

import java.util.ArrayList;
import java.util.List;
import org.keycloak.Config.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;

public class EnvVarScopeDecorator implements Scope {

  private final Scope scope;
  private final String prefix;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(EnvVarScopeDecorator.class);
  
  public EnvVarScopeDecorator(final String prefix, final Scope scope) {
    this.scope = scope;
    this.prefix = prefix;
  }
  
  @Override
  public String get(final String key) {
    final String value = getValue(key);
    if (value != null) {
      return value;
    }
    return this.scope.get(key);
  }

  @Override
  public String get(final String key, final String defaultValue) {
    final String value = getValue(key);
    if (value != null) {
      return value;
    }    
    return this.scope.get(key, defaultValue);
  }

  @Override
  public String[] getArray(final String key) {
    final String value = getValue(key);
    if (value != null) {
      return value.split(",");
    } 
    return this.scope.getArray(key);
  }

  @Override
  public Integer getInt(final String key) {
    final String value = getValue(key);
    if (value != null) {
      return Integer.parseInt(value);
    }
    return this.scope.getInt(key);
  }

  @Override
  public Integer getInt(final String key, final Integer defaultValue) {
    final String value = getValue(key);
    if (value != null) {
      return Integer.parseInt(value);
    }
    return this.scope.getInt(key, defaultValue);
  }

  @Override
  public Long getLong(final String key) {
    final String value = getValue(key);
    if (value != null) {
      return Long.parseLong(value);
    }
    return this.scope.getLong(key);
  }

  @Override
  public Long getLong(final String key, final Long defaultValue) {
    final String value = getValue(key);
    if (value != null) {
      return Long.parseLong(value);
    }    
    return this.scope.getLong(key, defaultValue);
  }

  @Override
  public Boolean getBoolean(final String key) {
    final String value = getValue(key);
    if (value != null) {
      return Boolean.parseBoolean(value);
    }
    return this.scope.getBoolean(key);
  }

  @Override
  public Boolean getBoolean(final String key, final Boolean defaultValue) {
    final String value = getValue(key);
    if (value != null) {
      return Boolean.parseBoolean(value);
    }
    return this.scope.getBoolean(key, defaultValue);
  }

  @Override
  public Scope scope(String... scope) {
    return this.scope.scope(scope);
  }

  protected String getValue(final String key) {
    final List<String> candidates = new ArrayList<>();
    candidates.add(toEnvVariable(getPrefix() + key));
    candidates.add(camelCaseToEnvVariable(getPrefix() + key));
    
    for (final String candidate : candidates) {
      LOGGER.debug("Retrieving from env {}...", candidate);
      final String getenv = System.getenv(candidate);
      if (getenv != null) {
        return getenv;
      }
    }
    return null;
  }
  
  protected static String toEnvVariable(String value) {
    if (value == null) {
      return null;
    }
    return value.trim().replace(".", "_").toUpperCase();
  }
  
  protected static String camelCaseToEnvVariable(String value) {
    if (value == null) {
      return null;
    }
    value = value.trim().replaceAll("[.]", "_");
    final StringBuilder sb = new StringBuilder();
    for (int i = 0; i < value.length(); i++) {
      final char charAt = value.charAt(i);
      if (Character.isUpperCase(charAt) && i > 0 && value.charAt(i - 1) != '_') {
        sb.append("_");
      }
      sb.append(charAt);
    }
    return sb.toString().trim().toUpperCase();
  }
  
  private String getPrefix() {
    return Strings.isNullOrEmpty(prefix) ? "" : prefix + ".";
  }
}
