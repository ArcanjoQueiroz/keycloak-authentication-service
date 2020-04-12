package br.com.alexandre.keycloak.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import br.com.alexandre.keycloak.spi.base.AbstractUserAdapter;

public class UserAdapter extends AbstractUserAdapter {

  private final User user;
  private Collection<Group> groups;
  private final String keycloakId;

  public UserAdapter(
      final KeycloakSession session,
      final RealmModel realm,
      final ComponentModel model,
      final User user,
      final Collection<Group> groups) {
    super(session, realm, model);
    this.user = user;
    this.groups = (groups == null) ? new ArrayList<>() : groups;
    this.keycloakId = StorageId.keycloakId(model, user.getUsername());
  }

  public UserAdapter(
      final KeycloakSession session,
      final RealmModel realm,
      final ComponentModel model,
      final User user) {
    this(session, realm, model, user, new ArrayList<>());
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
  public Set<GroupModel> getGroups() {
    final Set<GroupModel> groups = super.getGroups();

    final Set<GroupAdapter> legacy =
        this.groups.stream().map(g -> new GroupAdapter(g)).collect(Collectors.toSet());

    final Set<GroupModel> g = new HashSet<>();
    g.addAll(groups);
    g.addAll(legacy);

    return g;
  }

  protected Set<GroupModel> getGroupsInternal() {
    return
        this.groups.stream().map(g -> new GroupAdapter(g)).collect(Collectors.toSet());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((keycloakId == null) ? 0 : keycloakId.hashCode());
    result = prime * result + ((user == null) ? 0 : user.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    UserAdapter other = (UserAdapter) obj;
    if (keycloakId == null) {
      if (other.keycloakId != null) {
        return false;
      }
    } else if (!keycloakId.equals(other.keycloakId)) {
      return false;
    }
    if (user == null) {
      if (other.user != null) {
        return false;
      }
    } else if (!user.equals(other.user)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "UserAdapter [user=" + user + ", keycloakId=" + keycloakId + "]";
  }
}
