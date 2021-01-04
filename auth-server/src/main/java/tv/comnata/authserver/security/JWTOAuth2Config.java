package tv.comnata.authserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.Arrays;

@Configuration
public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private TokenStore tokenStore;
    private DefaultTokenServices tokenServices;
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    private CustomTokenEnhancer customTokenEnhancer;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailsService(@Qualifier("userDetailsServiceBean") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Autowired
    public void setTokenServices(DefaultTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Autowired
    public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
        this.jwtAccessTokenConverter = jwtAccessTokenConverter;
    }

    @Autowired
    public void setCustomTokenEnhancer(@Qualifier("tokenEnhancer") CustomTokenEnhancer customTokenEnhancer) {
        this.customTokenEnhancer = customTokenEnhancer;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer, jwtAccessTokenConverter));
        endpoints
                .tokenStore(tokenStore)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
    }

    @Bean
    public CustomTokenEnhancer tokenEnhancer() {
        return new CustomTokenEnhancer();
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey(serviceConfig.getJwtSigningKey());
        converter.setSigningKey("helloworld");
        return converter;
    }
}
