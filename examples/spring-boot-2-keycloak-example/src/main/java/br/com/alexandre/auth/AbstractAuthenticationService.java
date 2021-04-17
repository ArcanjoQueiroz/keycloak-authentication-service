package br.com.alexandre.auth;

import br.com.alexandre.auth.domain.AuthenticationService;
import br.com.alexandre.auth.domain.User;

public abstract class AbstractAuthenticationService implements AuthenticationService {

  private final ThreadLocal<User> threadLocal = new ThreadLocal<>();

  public synchronized User getUser() {
    if (threadLocal.get() != null) {
      return threadLocal.get();
    } else {
      final User authenticatedUser = getAuthenticatedUser();
      setUser(authenticatedUser);
      return authenticatedUser;
    }
  }

  public synchronized void setUser(final User user) {
    this.threadLocal.set(user);
  }

  protected abstract User getAuthenticatedUser();
}
