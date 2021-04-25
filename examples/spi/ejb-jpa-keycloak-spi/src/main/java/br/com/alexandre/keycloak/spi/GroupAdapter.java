package br.com.alexandre.keycloak.spi;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.keycloak.models.ClientModel;
import org.keycloak.models.GroupModel;
import org.keycloak.models.RoleModel;

@lombok.EqualsAndHashCode(of = { "group" })
public class GroupAdapter implements GroupModel {

  public final Group group;

  public GroupAdapter(final Group group) {
    this.group = group;
  }

  @Override
  public String getId() {
    return this.group.getGroupId();
  }

  @Override
  public String getName() {
    return this.group.getName();
  }

  @Override
  public void setName(final String name) {
    this.group.setName(name);
  }

  @Override
  public Set<RoleModel> getRealmRoleMappings() {
    return Collections.emptySet();
  }

  @Override
  public Set<RoleModel> getClientRoleMappings(ClientModel app) {
    return Collections.emptySet();
  }

  @Override
  public boolean hasRole(RoleModel role) {
    return false;
  }

  @Override
  public void grantRole(RoleModel role) {}

  @Override
  public Set<RoleModel> getRoleMappings() {
    return Collections.emptySet();
  }

  @Override
  public void deleteRoleMapping(RoleModel role) {}

  @Override
  public void setSingleAttribute(String name, String value) {}

  @Override
  public void setAttribute(String name, List<String> values) {}

  @Override
  public void removeAttribute(String name) {}

  @Override
  public String getFirstAttribute(String name) {
    return null;
  }

  @Override
  public List<String> getAttribute(String name) {
    return Collections.emptyList();
  }

  @Override
  public Map<String, List<String>> getAttributes() {
    return new HashMap<>();
  }

  @Override
  public GroupModel getParent() {
    return null;
  }

  @Override
  public String getParentId() {
    return null;
  }

  @Override
  public Set<GroupModel> getSubGroups() {
    return Collections.emptySet();
  }

  @Override
  public void setParent(GroupModel group) {}

  @Override
  public void addChild(GroupModel subGroup) {}

  @Override
  public void removeChild(GroupModel subGroup) {}
}
