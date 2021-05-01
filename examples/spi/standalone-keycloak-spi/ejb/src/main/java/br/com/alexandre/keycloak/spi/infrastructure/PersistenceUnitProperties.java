package br.com.alexandre.keycloak.spi.infrastructure;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.Builder
@lombok.ToString
class PersistenceUnitProperties implements Serializable {

  private static final long serialVersionUID = -5524177904298125139L;
  
  private String name;
  private String dialect;
  private String defaultSchema;
  private String physicalNamingStrategy;
  private Boolean useJdbcMetadataDefaults;  
  private Boolean generateStatistics;
  private Boolean showSql;
  private Boolean useSqlComments;
  private Boolean update;
    
}