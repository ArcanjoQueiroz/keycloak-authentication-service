package br.com.alexandre.keycloak.spi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.StorageId;
import com.google.common.base.Strings;
import br.com.alexandre.keycloak.spi.base.AbstractUserAdapter;

@lombok.ToString
@lombok.EqualsAndHashCode(callSuper = false, of = { "user", "keycloakId" })
public class UserAdapter extends AbstractUserAdapter {

  private static final String COMPANY_ID_ATTRIBUTE = "companyId";
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
  
  // Groups

  protected Set<GroupModel> getGroupsInternal() {
    return this.groups.stream()
        .map(g -> new GroupAdapter(g))
        .filter(ga -> !Strings.isNullOrEmpty(ga.getId()))
        .collect(Collectors.toSet());
  }
  
  // User Attributes
    
  @Override
  public void addUserAttributes(Map<String, List<String>> attributes) {
    if (user.getCompanyId() != null) {
      attributes.put(COMPANY_ID_ATTRIBUTE, Arrays.asList(user.getCompanyId().toString()));
    }
  }  
  
  @Override
  public void setSingleAttribute(final String name, final String value) {
    if (COMPANY_ID_ATTRIBUTE.equalsIgnoreCase(name)) {
      user.setCompanyId(Integer.parseInt(value));
    }
  }

  @Override
  public void setAttribute(final String name, final List<String> values) {
    if (!values.isEmpty()) {
      setSingleAttribute(name, values.get(0));
    }
  }  

}
