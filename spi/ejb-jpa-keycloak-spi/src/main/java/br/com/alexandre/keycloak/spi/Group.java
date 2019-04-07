package br.com.alexandre.keycloak.spi;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "GROUPS")
@SequenceGenerator(name = "GROUPS_SEQ", sequenceName = "GROUPS_SEQ", initialValue = 1, allocationSize = 1)
public class Group implements Serializable {

    private static final long serialVersionUID = -7817891841488600786L;

    @Id
    @Column(name = "ID", nullable = false, insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GROUPS_SEQ")
    private Long Id;
    
    @Column(name = "NAME", length = 30, nullable = false, insertable = false, updatable = false)
    private String name;
    
    public Group() { }

    public Long getId() {
      return Id;
    }

    public void setId(Long id) {
      Id = id;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((name == null) ? 0 : name.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Group other = (Group) obj;
      if (name == null) {
        if (other.name != null)
          return false;
      } else if (!name.equals(other.name))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "Group [Id=" + Id + ", name=" + name + "]";
    }
    
}
