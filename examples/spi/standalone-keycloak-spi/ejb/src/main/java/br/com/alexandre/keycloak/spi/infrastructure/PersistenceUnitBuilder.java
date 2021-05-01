package br.com.alexandre.keycloak.spi.infrastructure;

import java.util.List;
import java.util.UUID;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;

public class PersistenceUnitBuilder {
  
  private Boolean showSql;
  private String dialect;
  private String schema;
  private List<Class<?>> managedClasses;
  private DataSource dataSource;

  private PersistenceUnitBuilder() { }
  
  public static PersistenceUnitBuilder builder() {
    return new PersistenceUnitBuilder();
  }
  
  public EntityManagerFactory build() {
    final PersistenceUnitProperties persistenceUnitProperties = new PersistenceUnitProperties();
    persistenceUnitProperties.setName(String.format("PU-%s", UUID.randomUUID().toString()));
    persistenceUnitProperties.setDialect(dialect);
    persistenceUnitProperties.setShowSql(showSql);
    persistenceUnitProperties.setUseSqlComments(showSql);
    persistenceUnitProperties.setDefaultSchema(schema);
    persistenceUnitProperties.setGenerateStatistics(false);
    persistenceUnitProperties.setUpdate(true);

    return createEntityManagerFactory(persistenceUnitProperties);
  }
  
  private EntityManagerFactory createEntityManagerFactory(
      final PersistenceUnitProperties persistenceUnitProperties) {
    final HibernateContainerPersistenceUnitInfo persistenceUnitInfo = 
        new HibernateContainerPersistenceUnitInfo(
        persistenceUnitProperties.getName(), 
        managedClasses, persistenceUnitProperties)
        .setNonJtaDataSource(dataSource);

    return new HibernatePersistenceProvider()
        .createContainerEntityManagerFactory(persistenceUnitInfo, null);
  }
  
  public PersistenceUnitBuilder datasource(final DataSource dataSource) {
    this.dataSource = dataSource;
    return this;
  }
  
  public PersistenceUnitBuilder showSql(final Boolean showSql) {
    this.showSql = showSql;
    return this;
  }
  
  public PersistenceUnitBuilder dialect(final String dialect) {
    this.dialect = dialect;
    return this;
  }
  
  public PersistenceUnitBuilder managedClasses(final List<Class<?>> managedClasses) {
    this.managedClasses = managedClasses;
    return this;
  }
}