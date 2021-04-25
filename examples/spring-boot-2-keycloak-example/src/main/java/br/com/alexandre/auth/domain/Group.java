package br.com.alexandre.auth.domain;

import java.io.Serializable;

@lombok.Getter
@lombok.Setter
@lombok.NoArgsConstructor
@lombok.AllArgsConstructor
@lombok.EqualsAndHashCode
@lombok.ToString
public class Group implements Serializable {

  private static final long serialVersionUID = 385170505294919655L;

  private Long groupId;
  private String groupName;
}
