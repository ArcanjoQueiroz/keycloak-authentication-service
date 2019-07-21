package br.com.alexandre.auth;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import br.com.alexandre.auth.domain.AuthenticationService;
import br.com.alexandre.auth.domain.User;
import br.com.alexandre.auth.domain.UserDetailsService;

@Service
@Profile("test")
public class MockAuthenticationService extends AbstractAuthenticationService implements AuthenticationService {

    private Logger logger = LoggerFactory.getLogger(MockAuthenticationService.class);
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Override
    protected User getAuthenticatedUser() {
        logger.info("Returning mock authenticated user...");
        final User user = createUser();
        user.setJti("mock");
        user.setLevel(1L);
        return userDetailsService.getUserDetails(user);
    }

    protected User createUser() {
      return new User(1L, "mock", "mock", "mock", "mock@mock.com.br", Arrays.asList("ROLE_ADMIN"));
    }

}
