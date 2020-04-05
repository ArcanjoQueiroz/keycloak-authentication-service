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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "USERS_GROUPS",
      joinColumns = {@JoinColumn(name = "USER_ID")},
      inverseJoinColumns = {@JoinColumn(name = "GROUP_ID")})
  private Set<Group> groups = new HashSet<>();

  public User() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getBlocked() {
    return blocked;
  }

  public void setBlocked(String blocked) {
    this.blocked = blocked;
  }

  public Set<Group> getGroups() {
    return groups;
  }

  public void setGroups(Set<Group> groups) {
    this.groups = groups;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    User other = (User) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "User [id="
        + id
        + ", firstName="
        + firstName
        + ", lastName="
        + lastName
        + ", username="
        + username
        + ", email="
        + email
        + ", password="
        + password
        + ", blocked="
        + blocked
        + "]";
  }
}
