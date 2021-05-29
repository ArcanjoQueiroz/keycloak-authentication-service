package br.com.alexandre.keycloak.spi;

import org.keycloak.Config.Scope;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EjbJpaEventListenerProviderFactory implements EventListenerProviderFactory {

  @Override
  public EventListenerProvider create(final KeycloakSession session) {
    return new EjbJpaEventListenerProvider();
  }

  @Override
  public void init(final Scope config) { }

  @Override
  public void postInit(final KeycloakSessionFactory factory) { }

  @Override
  public void close() {
    log.info("Closing {}...", EjbJpaEventListenerProviderFactory.class.getSimpleName());    
  }

  @Override
  public String getId() {
    return "ejb-jpa-listener";
  }

}
