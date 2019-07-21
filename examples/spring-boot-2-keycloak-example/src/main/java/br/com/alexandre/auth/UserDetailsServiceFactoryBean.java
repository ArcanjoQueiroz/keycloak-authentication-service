package br.com.alexandre.auth;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import br.com.alexandre.auth.domain.UserDetailsService;

@Component
public class UserDetailsServiceFactoryBean {

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService getBean() {
        return new BasicUserDetailsService();
    }
}
