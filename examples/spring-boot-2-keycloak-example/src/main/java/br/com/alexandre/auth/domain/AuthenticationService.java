package br.com.alexandre.auth.domain;

public interface AuthenticationService {
    public User getUser();
    public void setUser(final User user);
}
