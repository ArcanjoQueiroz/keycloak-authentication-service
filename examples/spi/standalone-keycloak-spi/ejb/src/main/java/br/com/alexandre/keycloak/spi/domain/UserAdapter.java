package br.com.alexandre.keycloak.spi.domain;

import java.util.List;
import java.util.Map;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import br.com.alexandre.keycloak.spi.base.AbstractUserAdapter;

@lombok.ToString
@lombok.EqualsAndHashCode(callSuper = false, of = { "user", "keycloakId" })
public class UserAdapter extends AbstractUserAdapter {

  private final User user;
  private final String keycloakId;

  public UserAdapter(
      final KeycloakSession session,
      final RealmModel realm,
      final ComponentModel model,
      final User user) {
    super(session, realm, model);
    this.user = user;
    this.keycloakId = StorageId.keycloakId(model, user.getUsername());
  }
  
  @Override
  public String getId() {
    return keycloakId;
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public void setUsername(String username) {
    user.setUsername(username);
  }

  @Override
  public String getEmail() {
    return user.getEmail();
  }

  @Override
  public void setEmail(String email) {
    user.setEmail(email);
  }

  @Override
  public String getFirstName() {
    return user.getFirstName();
  }

  @Override
  public void setFirstName(String firstName) {
    user.setFirstName(firstName);
  }

  @Override
  public String getLastName() {
    return null;
  }

  @Override
  public void setLastName(String lastName) {}

  public String getPassword() {
    return user.getPassword();
  }

  public void setPassword(final String password) {
    this.user.setPassword(password);
  }

  @Override
  public void addUserAttributes(Map<String, List<String>> attributes) { }

}
