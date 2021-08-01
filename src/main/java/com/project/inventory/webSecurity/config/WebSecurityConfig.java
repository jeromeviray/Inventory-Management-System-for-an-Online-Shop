package com.project.inventory.webSecurity.config;

import com.project.inventory.webSecurity.filter.CustomAuthenticationFilter;
import com.project.inventory.webSecurity.filter.CustomAuthorizationFilter;
import com.project.inventory.webSecurity.oauth2.service.CustomOAuth2UserService;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true,
        prePostEnabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    static final String API = "api/v1/";
    @Autowired
    @Qualifier( value = "userDetailsServiceImpl" )
    private UserDetailsService userDetailsService;
    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

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
                .antMatchers( HttpMethod.POST,
                        "/api/v1/account/login",
                        "/api/v1/account/register" ).permitAll()
                .antMatchers( "/oauth2/**" ).permitAll()
                .antMatchers( HttpMethod.GET, "/api/v1/account/token/refresh" ).permitAll();
        //private endpoint
        http.authorizeRequests().anyRequest().authenticated();
        //add filter
        http.addFilter( new CustomAuthenticationFilter( authenticationManagerBean() ) );
        http.addFilterBefore( new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class );
        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
        // disable the default
        http.formLogin()
                .disable()
                .httpBasic()
                .disable();
        http.oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorize").and()
                .redirectionEndpoint()
                .baseUri( "/oauth2/callback/*" )
                .and()
                .userInfoEndpoint()
                .userService( customOAuth2UserService );
    }

//
//    // Used by spring security if CORS is enabled.
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration config = new CorsConfiguration();
//
//        config.setAllowCredentials( true );
//        config.addAllowedOrigin( "*" );
//        config.addAllowedHeader( "*" );
//        config.addAllowedMethod( "*" );
//        source.registerCorsConfiguration( "/**", config );
//        return new CorsFilter( source );
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();

    }

}
