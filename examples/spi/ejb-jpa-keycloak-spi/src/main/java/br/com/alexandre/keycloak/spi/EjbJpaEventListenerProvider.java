package br.com.alexandre.keycloak.spi;

import java.util.Map;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.admin.AdminEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EjbJpaEventListenerProvider implements EventListenerProvider {

  @Override
  public void close() {
    log.info("Closing {}...", EjbJpaEventListenerProvider.class.getSimpleName());
  }

  @Override
  public void onEvent(final Event event) {
    log.info("Event: {}", toString(event));    
  }

  @Override
  public void onEvent(final AdminEvent event, boolean includeRepresentation) { }

  private String toString(final Event event) {
    final StringBuilder sb = new StringBuilder()
        .append("type=")
        .append(event.getType())
        .append(", realmId=")
        .append(event.getRealmId())
        .append(", clientId=")
        .append(event.getClientId())
        .append(", userId=")
        .append(event.getUserId())
        .append(", ipAddress=")
        .append(event.getIpAddress());

    if (event.getError() != null) {
      sb.append(", error=")
        .append(event.getError());
    }
    if (event.getDetails() != null) {
      for (Map.Entry<String, String> e : event.getDetails().entrySet()) {
        sb.append(", ")
          .append(e.getKey());
        if (e.getValue() == null || e.getValue().indexOf(' ') == -1) {
          sb.append("=")
            .append(e.getValue());
        } else {
          sb.append("='")
            .append(e.getValue())
            .append("'");
        }
      }
    }
    return sb.toString();
  }


}
