package br.com.alexandre.keycloak.spi.infrastructure;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Strings;

class HibernateContainerPersistenceUnitInfo implements PersistenceUnitInfo {

  public static final String JPA_VERSION = "2.1";
  private final String persistenceUnitName;
  private PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType
      .RESOURCE_LOCAL;
  private final List<String> managedClassNames;
  private final List<String> mappingFileNames = new ArrayList<>();
  private final Properties properties;
  private DataSource jtaDataSource;
  private DataSource nonjtaDataSource;
  private final List<ClassTransformer> transformers = new ArrayList<>();

  private final Logger logger = LoggerFactory
      .getLogger(HibernateContainerPersistenceUnitInfo.class);

  public HibernateContainerPersistenceUnitInfo(final String persistenceUnitName, 
      final List<Class<?>> managedClassNames, 
      final PersistenceUnitProperties properties) {
    this.persistenceUnitName = persistenceUnitName;
    this.managedClassNames = asList(managedClassNames);
    this.properties = asProperties(properties);
  }

  protected List<String> asList(final List<Class<?>> managedClassNames) {
    if (managedClassNames == null) {
      return Collections.emptyList();
    }
    final List<String> managedClasses = managedClassNames
        .stream()
        .map(d -> d.getName())
        .collect(Collectors.toList());
    for (final String managedClass : managedClasses) {
      logger.info("Mapping class: '{}'", managedClass);
    }
    return managedClasses;
  }

  @Override
  public String getPersistenceUnitName() {
    return persistenceUnitName;
  }

  @Override
  public String getPersistenceProviderClassName() {
    return "org.hibernate.jpa.HibernatePersistenceProvider";
  }

  @Override
  public PersistenceUnitTransactionType getTransactionType() {
    return transactionType;
  }

  @Override
  public DataSource getJtaDataSource() {
    return jtaDataSource;
  }

  @Override
  public DataSource getNonJtaDataSource() {
    return nonjtaDataSource;
  }

  @Override
  public List<String> getMappingFileNames() {
    return mappingFileNames;
  }

  @Override
  public List<URL> getJarFileUrls() {
    return Collections.emptyList();
  }

  @Override
  public URL getPersistenceUnitRootUrl() {
    return null;
  }

  @Override
  public List<String> getManagedClassNames() {
    return managedClassNames;
  }

  @Override
  public boolean excludeUnlistedClasses() {
    return true;
  }

  @Override
  public SharedCacheMode getSharedCacheMode() {
    return SharedCacheMode.UNSPECIFIED;
  }

  @Override
  public ValidationMode getValidationMode() {
    return ValidationMode.AUTO;
  }

  @Override
  public Properties getProperties() {
    return properties;
  }

  @Override
  public String getPersistenceXMLSchemaVersion() {
    return JPA_VERSION;
  }

  @Override
  public ClassLoader getClassLoader() {
    return Thread.currentThread().getContextClassLoader();
  }

  @Override
  public void addTransformer(final ClassTransformer transformer) {
    transformers.add(transformer);    
  }

  @Override
  public ClassLoader getNewTempClassLoader() {
    return null;
  }

  public HibernateContainerPersistenceUnitInfo setJtaDataSource(
      final DataSource jtaDataSource) {
    this.jtaDataSource = jtaDataSource;
    this.nonjtaDataSource = null;
    transactionType = PersistenceUnitTransactionType.JTA;
    return this;
  }


  public HibernateContainerPersistenceUnitInfo setNonJtaDataSource(
      final DataSource nonJtaDataSource) {
    this.nonjtaDataSource = nonJtaDataSource;
    this.jtaDataSource = null;
    transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    return this;
  }

  private Properties asProperties(final PersistenceUnitProperties properties) {    
    final Properties hibernateProperties = new Properties();    

    if (!Strings.isNullOrEmpty(properties.getDialect())) {
      hibernateProperties.put("hibernate.dialect", properties.getDialect());
    }    
    if (!Strings.isNullOrEmpty(properties.getDefaultSchema())) {
      hibernateProperties.put("hibernate.default_schema", 
          properties.getDefaultSchema());
    }    
    if (!Strings.isNullOrEmpty(properties.getPhysicalNamingStrategy())) {
      hibernateProperties.put("hibernate.physical_naming_strategy", 
          properties.getPhysicalNamingStrategy());
    }
    if (properties.getUseJdbcMetadataDefaults() != null) {
      hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", 
          properties.getUseJdbcMetadataDefaults());
    }
    if (properties.getGenerateStatistics() != null) {
      hibernateProperties.put("hibernate.generate_statistics", 
          properties.getGenerateStatistics());
    }
    if (properties.getShowSql() != null) {
      hibernateProperties.put("hibernate.show_sql", properties.getShowSql());
      hibernateProperties.put("hibernate.format_sql", properties.getShowSql());
      if (properties.getUseSqlComments() != null) {
        hibernateProperties.put("hibernate.use_sql_comments", properties.getUseSqlComments());
      }
    }
    
    if (Boolean.TRUE.equals(properties.getUpdate())) {
      hibernateProperties.put("hibernate.hbm2ddl.auto", "update");
    }
    
    hibernateProperties.put("hibernate.jdbc.use_scrollable_resultset", true);
    hibernateProperties.put("hibernate.jdbc.use_get_generated_keys", true);
    hibernateProperties.put("hibernate.proc.param_null_passing", true);
    hibernateProperties.put("hibernate.temp.use_jdbc_metadata_defaults", false);

    logger.info("Default Schema: '{}', Physical Naming Strategy: '{}', "
        + "Use JDBC Metadata Defaults: '{}'",
        properties.getDefaultSchema(), properties.getPhysicalNamingStrategy(), 
        properties.getUseJdbcMetadataDefaults());    

    return hibernateProperties;
  }


}