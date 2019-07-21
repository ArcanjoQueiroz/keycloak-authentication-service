package br.com.alexandre.auth;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class AuthenticationContext {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    private AuthenticationContext() { }
    
    public static String getAccessToken() {
        final SecurityContext context = SecurityContextHolder.getContext();
        String accessToken = null;
        if (context.getAuthentication() instanceof OAuth2Authentication) {
            final OAuth2Authentication authentication = (OAuth2Authentication) context.getAuthentication();
            if (authentication != null && authentication.getDetails() instanceof OAuth2AuthenticationDetails) {
                final OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
                accessToken = details.getTokenValue();
            }
        }  
        return accessToken;
    }
}
