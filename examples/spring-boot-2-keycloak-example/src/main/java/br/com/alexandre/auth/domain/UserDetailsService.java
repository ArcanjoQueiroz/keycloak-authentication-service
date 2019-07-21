package br.com.alexandre.auth.domain;

public interface UserDetailsService {
    public User getUserDetails(final User user);
}
