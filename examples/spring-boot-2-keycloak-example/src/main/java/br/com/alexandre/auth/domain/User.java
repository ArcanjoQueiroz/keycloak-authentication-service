package br.com.alexandre.auth.domain;

import static java.util.Objects.isNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User implements Serializable {

    private static final long serialVersionUID = -7008087963833718951L;

    private String jti;
    private Long userId;
    private String userName;
    private String givenName;
    private String familyName;
    private Long level;
    private String email;
    private List<String> roles = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private Company company;
    
    public User() { }
    
    public User(final Long userId, final String userName, final String givenName, final String familyName, final String email, final List<String> roles) {
        this.userId = userId;
        this.userName = userName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.email = email;
        this.roles = roles;
    }

    public List<Group> getGroups() {
        return isNull(groups) ? Collections.emptyList() : Collections.unmodifiableList(groups);
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
    
    public List<String> getRoles() {
        return isNull(roles) ? Collections.emptyList() : Collections.unmodifiableList(roles);
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
        User other = (User) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [jti=" + jti + ", userId=" + userId + ", userName=" + userName + ", givenName=" + givenName
                + ", familyName=" + familyName + ", level=" + level +  ", email=" + email + ", roles=" + roles + ", company=" + company + "]";
    }
}
