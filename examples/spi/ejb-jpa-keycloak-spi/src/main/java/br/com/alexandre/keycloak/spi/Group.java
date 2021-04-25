package br.com.alexandre.keycloak.spi;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(of = { "id" })
@lombok.ToString
@Entity
@Table(name = "GROUPS")
@SequenceGenerator(
    name = "GROUPS_SEQ",
    sequenceName = "GROUPS_SEQ",
    initialValue = 1,
    allocationSize = 1)
public class Group implements Serializable {

  private static final long serialVersionUID = -7817891841488600786L;

  @Id
  @Column(name = "ID", nullable = false, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUPS_SEQ")
  private Long id;

  @Column(name = "NAME", length = 30, nullable = false, insertable = false, updatable = false)
  private String name;
  
  @Column(name = "GROUP_ID", length = 48)
  private String groupId;
}
