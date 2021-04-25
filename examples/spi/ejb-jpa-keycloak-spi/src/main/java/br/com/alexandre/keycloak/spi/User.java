package br.com.alexandre.keycloak.spi;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(of = { "id" })
@lombok.ToString(exclude = { "groups" })
@Entity
@Table(name = "USERS")
@NamedQueries(
    value = {
      @NamedQuery(name = "count", query = "select count(u) from User u where u.blocked = 'N'"),
      @NamedQuery(
          name = "findAll",
          query = "select u from User u where u.blocked = 'N'"),
      @NamedQuery(
          name = "findByUsername",
          query =
              "select u from User u where u.blocked = 'N' "
              + "and lower(u.username) = :username"),
      @NamedQuery(
          name = "findByEmail",
          query =
              "select u from User u where u.blocked = 'N' "
              + "and lower(u.email) = :email"),
      @NamedQuery(
          name = "findByUsernameOrEmail",
          query =
              "select u from User u where u.blocked = 'N' "
              + "and (lower(u.username) = :search or lower(u.email) = :search)"),
    })
@SequenceGenerator(
    name = "USERS_SEQ",
    sequenceName = "USERS_SEQ",
    initialValue = 1,
    allocationSize = 1)
public class User implements Serializable {

  private static final long serialVersionUID = -2256819749651910998L;

  @Id
  @Column(name = "ID", nullable = false, unique = true)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_SEQ")
  private Long id;

  @Column(name = "FIRST_NAME", length = 80, nullable = false)
  private String firstName;

  @Column(name = "LAST_NAME", length = 80, nullable = false)
  private String lastName;

  @Column(name = "USERNAME", length = 30, nullable = false, unique = true)
  private String username;

  @Column(name = "EMAIL", length = 60, nullable = true)
  private String email;

  @Column(name = "PASSWORD", length = 40, nullable = false)
  private String password;

  @Column(name = "BLOCKED", length = 1, nullable = false)
  private String blocked;
  
  @Column(name = "COMPANY_ID", nullable = false)
  private Integer companyId;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "USERS_GROUPS",
      joinColumns = {@JoinColumn(name = "USER_ID")},
      inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
  private Set<Group> groups = new HashSet<>();
}
