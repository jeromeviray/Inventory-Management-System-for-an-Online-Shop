package com.project.inventory.webSecurity.config;

import com.project.inventory.exception.impl.AccessDeniedExceptionImpl;
import com.project.inventory.exception.impl.AuthenticationEntryPointImpl;
import com.project.inventory.webSecurity.filter.CustomAuthenticationFilter;
import com.project.inventory.webSecurity.filter.CustomAuthorizationFilter;
import com.project.inventory.webSecurity.oauth2.cookie.HttpCookieOAuth2RequestRepository;
import com.project.inventory.webSecurity.oauth2.failureHandler.OAuth2AuthenticationFailureHandler;
import com.project.inventory.webSecurity.oauth2.service.CustomOAuth2UserService;
import com.project.inventory.webSecurity.oauth2.successHandler.OAuth2AuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    static final String API = "/api/v1/";
    @Autowired
    @Qualifier( value = "userDetailsServiceImpl" )
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;
    @Autowired
    private HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;
    @Autowired
    private OAuth2AuthenticationSuccessHandler auth2AuthenticationSuccessHandler;
    @Autowired
    private OAuth2AuthenticationFailureHandler auth2AuthenticationFailureHandler;
    @Autowired
    private AccessDeniedExceptionImpl accessDeniedException;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure( AuthenticationManagerBuilder auth ) throws Exception {
        auth.userDetailsService( userDetailsService );
    }



    @Override
    protected void configure( HttpSecurity http ) throws Exception {

        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();
        //public endpoint
        http.authorizeRequests()
                .antMatchers("/websocket/**").permitAll()
                .antMatchers( HttpMethod.POST,
                        "/api/v1/account/login",
                        "/api/v1/users/account/register",
                        "/api/v1/users/account/token/refresh"
                        ).permitAll()
                .antMatchers( HttpMethod.GET, "/api/v1/products/getImages/bytesArrays/**",
                        "/api/v1/comments",
                        "/api/v1/products/discover",
                        "/api/v1/products/details/**",
                        "/api/v1/products/search",
                        "/api/v1/products/category/**",
                        "/api/v1/categories/list").permitAll()
                .antMatchers( "/oauth2/**" ).permitAll();
        http.authorizeRequests().anyRequest().authenticated();
        // Set unauthorized and access denied requests exception handler
        http.exceptionHandling()
                .accessDeniedHandler( accessDeniedException )
                .authenticationEntryPoint( authenticationEntryPoint );

        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        // disable the default
        http.formLogin()
                .disable()
                .httpBasic()
                .disable();
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri( "/oauth2/authorize" )
                .authorizationRequestRepository( httpCookieOAuth2RequestRepository )
                .and()
                .redirectionEndpoint()
                .baseUri( "/oauth2/callback/*" )
                .and()
                .userInfoEndpoint()
                .userService( customOAuth2UserService )
                .and()
                .successHandler( auth2AuthenticationSuccessHandler )
                .failureHandler( auth2AuthenticationFailureHandler );
        //add filter
        http.addFilter( new CustomAuthenticationFilter( authenticationManagerBean() ) );
        http.addFilterBefore( new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

}
