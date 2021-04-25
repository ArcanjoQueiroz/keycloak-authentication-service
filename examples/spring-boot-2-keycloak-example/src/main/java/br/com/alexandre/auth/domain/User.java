package br.com.alexandre.auth.domain;

import static java.util.Objects.isNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.EqualsAndHashCode(of = { "userName" })
@lombok.ToString
public class User implements Serializable {

  private static final long serialVersionUID = -7008087963833718951L;

  private String azp;
  private String jti;
  private Long userId;
  private String userName;
  private String givenName;
  private String familyName;
  private Long level;
  private String email;
  private List<String> roles;
  private List<Group> groups;
  private Long companyId;

  public User(
      final Long userId,
      final String userName,
      final String givenName,
      final String familyName,
      final String email,
      final List<String> roles,
      final Long companyId) {
    this.userId = userId;
    this.userName = userName;
    this.givenName = givenName;
    this.familyName = familyName;
    this.email = email;
    this.roles = roles;
    this.companyId = companyId;
  }

  public List<Group> getGroups() {
    return isNull(groups) ? Collections.emptyList() : Collections.unmodifiableList(groups);
  }

  public List<String> getRoles() {
    return isNull(roles) ? Collections.emptyList() : Collections.unmodifiableList(roles);
  }

}
