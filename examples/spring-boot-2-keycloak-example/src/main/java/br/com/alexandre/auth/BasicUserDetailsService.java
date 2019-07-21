package br.com.alexandre.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import br.com.alexandre.auth.domain.User;
import br.com.alexandre.auth.domain.UserDetailsService;

public class BasicUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(BasicUserDetailsService.class);
    
    @Override
    public User getUserDetails(final User user) {
        logger.debug("Using basic User Details Service for user: '{}'...", user.getUserName());
        return user;
    }

}
