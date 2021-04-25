package br.com.alexandre.keycloak.spi;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;

public class EjbJpaUserStorageProviderFactory
    implements UserStorageProviderFactory<EjbJpaUserStorageProvider> {

  private static final String USER_STORAGE_FACTORY_NAME = "ejb-jpa-user-storage-factory";
  
  private static final String SPI_JNDI_NAME = "ejb-jpa-keycloak-spi";
  
  private static final Logger LOGGER = Logger.getLogger(EjbJpaUserStorageProviderFactory.class);

  @Override
  public EjbJpaUserStorageProvider create(KeycloakSession session, ComponentModel model) {
    EjbJpaUserStorageProvider storageProvider;
    LOGGER.info("Creating User Storage Provider...");
    try {
      final InitialContext ctx = new InitialContext();
      final UserRepository userRepository =
          (UserRepository)
              ctx.lookup(
                  String.format("java:global/%s/%s", 
                      SPI_JNDI_NAME, 
                      UserRepository.class.getSimpleName()));
      storageProvider = new EjbJpaUserStorageProvider(session, model, userRepository);
    } catch (final NamingException e) {
      throw new RuntimeException("Error on creating User Storage Provider: " + e.getMessage(), e);
    }
    LOGGER.info("Created User Storage Provider...");
    return storageProvider;
  }

  @Override
  public String getId() {
    return USER_STORAGE_FACTORY_NAME;
  }
}
