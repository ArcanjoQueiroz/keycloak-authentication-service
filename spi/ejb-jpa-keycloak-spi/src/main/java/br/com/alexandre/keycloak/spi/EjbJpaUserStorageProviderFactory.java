package br.com.alexandre.keycloak.spi;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class EjbJpaUserStorageProviderFactory
    implements UserStorageProviderFactory<EjbJpaUserStorageProvider> {

  private static final Logger logger = Logger.getLogger(EjbJpaUserStorageProviderFactory.class);

  @Override
  public EjbJpaUserStorageProvider create(KeycloakSession session, ComponentModel model) {
    EjbJpaUserStorageProvider storageProvider = null;
    logger.info("Creating User Storage Provider...");
    try {
      final InitialContext ctx = new InitialContext();
      final UserRepository userRepository =
          (UserRepository)
              ctx.lookup(
                  "java:global/ejb-jpa-keycloak-spi/" + UserRepository.class.getSimpleName());
      storageProvider = new EjbJpaUserStorageProvider(session, model, userRepository);
    } catch (NamingException e) {
      throw new RuntimeException("Error on creating User Storage Provider: " + e.getMessage(), e);
    }
    logger.info("Created User Storage Provider...");
    return storageProvider;
  }

  @Override
  public String getId() {
    return "ejb-jpa-user-storage-factory";
  }
}
