package br.com.alexandre.keycloak.spi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.models.GroupModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.credential.PasswordCredentialModel;
import org.keycloak.storage.StorageId;
import br.com.alexandre.keycloak.spi.base.AbstractUserStorageProvider;

public class EjbJpaUserStorageProvider extends AbstractUserStorageProvider {

  private static final String USERS_QUERY_SEARCH = "keycloak.session.realm.users.query.search";

  private static final String ADMIN = "admin";

  private static final Logger logger = Logger.getLogger(EjbJpaUserStorageProvider.class);

  private final KeycloakSession session;
  private final ComponentModel model;
  private final UserRepository userRepository;

  public static final String PASSWORD_CACHE_KEY = UserAdapter.class.getName() + ".password";

  public EjbJpaUserStorageProvider(
      final KeycloakSession session,
      final ComponentModel model,
      final UserRepository userRepository) {
    this.session = session;
    this.model = model;
    this.userRepository = userRepository;
  }

  @Override
  public boolean isValid(
      final RealmModel realm, final UserModel user, final CredentialInput input) {
    if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
      return false;
    }
    final UserCredentialModel cred = (UserCredentialModel) input;
    final boolean valid =
        this.userRepository.validateCredentials(user.getUsername(), cred.getValue());
    logger.info("User " + user.getUsername() + " is " + ((valid) ? "valid" : "invalid"));
    return valid;
  }

  @Override
  public boolean updateCredential(
      final RealmModel realm, final UserModel user, final CredentialInput input) {
    if (!supportsCredentialType(input.getType()) || !(input instanceof UserCredentialModel)) {
      return false;
    }
    final UserCredentialModel cred = (UserCredentialModel) input;
    logger.info("Updating user " + user.getUsername() + " credentials...");
    final User updateCredentials =
        this.userRepository.updateCredentials(user.getUsername(), cred.getValue());
    final boolean updated = updateCredentials != null;
    if (updated) {
      getUserAdapter(user).setPassword(updateCredentials.getPassword());
      logger.info("Credentials was successfully updated");
    }
    return updated;
  }

  @Override
  public UserModel addUser(final RealmModel realm, final String username) {
    if (username != null && !ADMIN.equalsIgnoreCase(username)) {
      final User user = this.userRepository.save(username);
      logger.info("Saving user " + username + " ...");
      return new UserAdapter(session, realm, model, user);
    } else {
      return null;
    }
  }

  @Override
  public boolean removeUser(final RealmModel realm, final UserModel user) {
    final String externalId = StorageId.externalId(user.getId());
    logger.info("Removing user by ID: " + externalId);
    final boolean remove = this.userRepository.remove(externalId);
    if (remove) {
      logger.info("User with ID " + externalId + " was successfully removed");
    }
    return remove;
  }

  @Override
  public int getUsersCount(final RealmModel realm) {
    final int usersCount = this.userRepository.getUsersCount();
    logger.info("Users Count: " + usersCount);
    return usersCount;
  }

  @Override
  public List<UserModel> getUsers(final RealmModel realm) {
    final List<UserModel> users =
        this.userRepository.findAll().stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return users;
  }

  @Override
  public List<UserModel> getUsers(
      final RealmModel realm, final int firstResult, final int maxResults) {
    final List<UserModel> users =
        this.userRepository.findAll(firstResult, maxResults).stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return users;
  }

  @Override
  public List<UserModel> searchForUser(
      Map<String, String> params, RealmModel realm, int firstResult, int maxResults) {    
    Collection<User> users = new ArrayList<User>();
    if (params.isEmpty()) {
      users = this.userRepository.findAll();
    } else if (params.containsKey(USERS_QUERY_SEARCH)) {
      final String search = params.get(USERS_QUERY_SEARCH);
      users = this.userRepository.findAllByUsernameOrEmail(search, firstResult, maxResults);
    }    
    final List<UserModel> usersModel = users.stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return usersModel;  
  }
  
  @Override
  public List<UserModel> searchForUser(Map<String, String> params, RealmModel realm) {
    Collection<User> users = new ArrayList<User>();
    if (params.isEmpty()) {
      users = this.userRepository.findAll();
    } else if (params.containsKey(USERS_QUERY_SEARCH)) {
      final String search = params.get(USERS_QUERY_SEARCH);
      users = this.userRepository.findAllByUsernameOrEmail(search);
    }    
    final List<UserModel> usersModel = users.stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return usersModel;  
  }

  @Override
  public List<UserModel> searchForUser(final String search, final RealmModel realm) {
    final List<UserModel> users =
        this.userRepository.findAllByUsernameOrEmail(search).stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return users;
  }

  @Override
  public List<UserModel> searchForUser(
      final String search, final RealmModel realm, final int firstResult, final int maxResults) {
    final List<UserModel> users =
        this.userRepository.findAllByUsernameOrEmail(search, firstResult, maxResults).stream()
        .map(user -> new UserAdapter(session, realm, model, user, user.getGroups()))
        .collect(Collectors.toList());
    logger.info("Returned " + users.size() + " users");
    return users;
  }

  @Override
  public UserModel getUserById(final String id, final RealmModel realm) {
    final String externalId = StorageId.externalId(id);
    logger.info("ID: " + id + ", External Id: " + externalId);
    final User user = this.userRepository.findUserByUsername(externalId);
    if (user == null) {
      logger.warn("User with ID " + id + " not found");
      return null;
    } else {
      logger.info("User " + user.getUsername() + " found by id " + externalId);

      return new UserAdapter(session, realm, model, user, user.getGroups());
    }
  }

  @Override
  public UserModel getUserByUsername(final String username, final RealmModel realm) {
    final User user = this.userRepository.findUserByUsername(username);
    if (user == null) {
      logger.warn("User with username " + username + " not found");
      return null;
    } else {
      logger.info("User " + user.getUsername() + " found by username " + username);

      return new UserAdapter(session, realm, model, user, user.getGroups());
    }
  }

  @Override
  public UserModel getUserByEmail(final String email, final RealmModel realm) {
    final User user = this.userRepository.findUserByEmail(email);
    if (user == null) {
      logger.warn("User with email " + email + " not found");
      return null;
    } else {
      logger.info("User " + user.getUsername() + " found by email " + email);

      return new UserAdapter(session, realm, model, user, user.getGroups());
    }
  }

  @Override
  public List<UserModel> getGroupMembers(
      RealmModel realm, GroupModel group, int firstResult, int maxResults) {
    logger.info("Finding group members by group " + group.getName() + " ...");
    return Collections.emptyList();
  }

  @Override
  public List<UserModel> getGroupMembers(RealmModel realm, GroupModel group) {
    logger.info("Finding group members by group " + group.getName() + " ...");
    return Collections.emptyList();
  }

  @Override
  public boolean supportsCredentialType(final String credentialType) {
    return PasswordCredentialModel.TYPE.equals(credentialType);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onCache(
      final RealmModel realm, final CachedUserModel user, final UserModel delegate) {
    final String password = ((UserAdapter) delegate).getPassword();
    if (password != null) {
      user.getCachedWith().put(PASSWORD_CACHE_KEY, password);
    }
  }

  @Override
  public void disableCredentialType(
      final RealmModel realm, final UserModel user, final String credentialType) {
    if (!supportsCredentialType(credentialType)) {
      return;
    }
    getUserAdapter(user).setPassword(null);
  }

  @Override
  public Set<String> getDisableableCredentialTypes(final RealmModel realm, final UserModel user) {
    if (getUserAdapter(user).getPassword() != null) {
      final Set<String> set = new HashSet<>();
      set.add(PasswordCredentialModel.TYPE);
      return set;
    } else {
      return Collections.emptySet();
    }
  }

  @Override
  public void close() {
    logger.debug("Closing " + EjbJpaUserStorageProvider.class.getSimpleName() + "...");
  }
}
