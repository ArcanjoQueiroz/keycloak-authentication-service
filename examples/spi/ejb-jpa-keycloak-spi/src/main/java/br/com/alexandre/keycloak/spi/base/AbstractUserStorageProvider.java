package br.com.alexandre.keycloak.spi.base;

import java.util.Collections;
import java.util.List;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.models.cache.CachedUserModel;
import org.keycloak.models.cache.OnUserCache;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;
import org.keycloak.storage.user.UserQueryProvider;
import org.keycloak.storage.user.UserRegistrationProvider;
import br.com.alexandre.keycloak.spi.UserAdapter;

public abstract class AbstractUserStorageProvider
    implements UserStorageProvider,
        UserLookupProvider,
        UserRegistrationProvider,
        UserQueryProvider,
        CredentialInputUpdater,
        CredentialInputValidator,
        OnUserCache {

  @Override
  public boolean isConfiguredFor(
      final RealmModel realm, final UserModel user, final String credentialType) {
    return supportsCredentialType(credentialType);
  }

  @Override
  public List<UserModel> searchForUserByUserAttribute(
      String attrName, String attrValue, RealmModel realm) {
    return Collections.emptyList();
  }

  protected UserAdapter getUserAdapter(final UserModel user) {
    return (user instanceof CachedUserModel)
        ? (UserAdapter) ((CachedUserModel) user).getDelegateForUpdate()
        : (UserAdapter) user;
  }
}
