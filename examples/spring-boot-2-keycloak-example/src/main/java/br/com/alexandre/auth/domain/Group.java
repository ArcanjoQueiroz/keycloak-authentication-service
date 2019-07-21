package br.com.alexandre.auth.domain;

import java.io.Serializable;
import java.util.Objects;

public class Group implements Serializable {

    private static final long serialVersionUID = 385170505294919655L;
    
    private Long groupId;
    private String groupName;

    public Group() { }

    public Group(Long groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int hashCode() {
        int result = groupId != null ? groupId.hashCode() : 0;
        result = 31 * result + (groupName != null ? groupName.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group = (Group) o;

        if (!Objects.equals(groupId, group.groupId)) return false;
        return Objects.equals(groupName, group.groupName);
    }

    @Override
    public String toString() {
        return "Group [groupId=" + groupId + ", groupName=" + groupName + ']';
    }

}
