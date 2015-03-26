package com.msaas.security;

import static com.msaas.rest.MainController.PATH_SCROLL_SCREEN;
import static com.msaas.rest.MainController.PATH_USER_DETAILS;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import com.msaas.model.Role;
import com.msaas.rest.MainController;

@Configuration
public class OauthConfiguration {

    private static final String RESOURCE_ID = "msaas";
    private static final String CLIENT_SECRET = "jhfads07ay7qwhcrq6787436ghrc8q3746fgx8347fgj97634gfx9j3467fg927";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .antMatchers(PATH_SCROLL_SCREEN).hasAnyRole(Role.OBSERVER.toString(), Role.ADMIN.toString())
                .antMatchers(PATH_USER_DETAILS).hasAnyRole(Role.CUSTOMER.toString(), Role.ADMIN.toString());
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
        private TokenStore tokenStore = new InMemoryTokenStore();

        @Resource
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
            endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                .inMemory()
                .withClient("mSaasWebClient")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities(Role.OBSERVER.toString(), Role.ADMIN.toString(), Role.CUSTOMER.toString())
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID)
                .secret(CLIENT_SECRET);
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }
    }
}
