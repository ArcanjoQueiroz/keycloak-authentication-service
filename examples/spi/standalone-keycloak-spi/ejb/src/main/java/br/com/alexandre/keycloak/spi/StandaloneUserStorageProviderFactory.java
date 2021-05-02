package br.com.alexandre.keycloak.spi;

import java.util.Arrays;
import javax.persistence.EntityManagerFactory;
import org.jboss.logging.Logger;
import org.keycloak.Config.Scope;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.storage.UserStorageProviderFactory;
import com.zaxxer.hikari.HikariDataSource;
import br.com.alexandre.keycloak.spi.base.EnvVarScopeDecorator;
import br.com.alexandre.keycloak.spi.domain.User;
import br.com.alexandre.keycloak.spi.domain.UserRepository;
import br.com.alexandre.keycloak.spi.infrastructure.DataSourceProperties;
import br.com.alexandre.keycloak.spi.infrastructure.HikariDataSourceBuilder;
import br.com.alexandre.keycloak.spi.infrastructure.PersistenceUnitBuilder;

public class StandaloneUserStorageProviderFactory
    implements UserStorageProviderFactory<StandaloneUserStorageProvider> {

  private static final String USER_STORAGE_FACTORY_NAME = "standalone-keycloak-spi";

  private static final Logger LOGGER = Logger.getLogger(StandaloneUserStorageProviderFactory.class);

  private EntityManagerFactory entityManagerFactory;
  
  private HikariDataSource dataSource;

  @Override
  public void init(final Scope config) {
    configure(new EnvVarScopeDecorator(config));
  }

  private synchronized void configure(final Scope config) {
    final DataSourceProperties properties = DataSourceProperties.builder()
        .driverClassName(config.get("driverClassName", "org.h2.Driver"))
        .url(config.get("url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL;DATABASE_TO_LOWER=true;"))
        .username(config.get("username", "sa"))
        .password(config.get("password", "sa"))
        .build();
        
    this.dataSource = HikariDataSourceBuilder.builder()
        .dataSourceProperties(properties)
        .build();
    this.entityManagerFactory = PersistenceUnitBuilder.builder()
      .datasource(dataSource)
      .dialect(config.get("dialect", "org.hibernate.dialect.H2Dialect"))
      .managedClasses(Arrays.asList(User.class))
      .showSql(config.getBoolean("showSql", true))
      .build();
  }
  
  @Override
  public StandaloneUserStorageProvider create(KeycloakSession session, ComponentModel model) {
    LOGGER.info("Creating User Storage Provider...");
    final UserRepository userRepository = new UserRepository(entityManagerFactory);

    final StandaloneUserStorageProvider storageProvider = 
        new StandaloneUserStorageProvider(session, model, userRepository);

    LOGGER.info("Created User Storage Provider...");
    return storageProvider;
  }

  @Override
  public String getId() {
    return USER_STORAGE_FACTORY_NAME;
  }

  @Override
  public void close() {
    if (this.entityManagerFactory != null && this.entityManagerFactory.isOpen()) {
      try {
        entityManagerFactory.close();
      } catch (final RuntimeException e) {
        LOGGER.error("Error on close resource: ", e);
      }
    }
    if (this.dataSource != null && !this.dataSource.isClosed()) {
      this.dataSource.close();
    }
  }
}
