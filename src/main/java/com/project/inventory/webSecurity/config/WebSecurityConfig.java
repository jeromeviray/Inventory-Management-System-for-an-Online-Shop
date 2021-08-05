package com.project.inventory.webSecurity.config;

import com.project.inventory.exception.impl.AccessDeniedExceptionImpl;
import com.project.inventory.exception.impl.AuthenticationEntryPointImpl;
import com.project.inventory.webSecurity.filter.CustomAuthenticationFilter;
import com.project.inventory.webSecurity.filter.CustomAuthorizationFilter;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


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
    @Qualifier(value = "userDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;
    @Autowired
    private AccessDeniedExceptionImpl accessDeniedExceptionImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // Enable CORS and disable CSRF
        http.cors().and().csrf().disable();
        // Set session management to stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //public endpoint
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,
                        "/api/v1/account/login",
                        "/api/v1/account/register",
                        "/api/v1/account/token/refresh").permitAll();
        //private endpoint
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/v1/products").hasAnyRole("ROLE_OWNER");

        http.authorizeRequests().anyRequest().authenticated();
        //add filter
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        // Set unauthorized and access denied requests exception handler
        http.exceptionHandling()
                .accessDeniedHandler(accessDeniedExceptionImpl)
                .authenticationEntryPoint(authenticationEntryPoint);

        // Set permissions on endpoints
//        http.authorizeRequests()
        // Our public endpoints
//            .antMatchers(HttpMethod.POST,"/api/v1/account/**").permitAll()
//                .antMatchers(HttpMethod.POST, "api/v1/user/login/**").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/v1/products/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/v1/account/**").permitAll()
//                .antMatchers(HttpMethod.POST,"/api/v1/store/**").permitAll()
//                .antMatchers(HttpMethod.GET,"/api/v1/account/information/**").permitAll()
//                .anyRequest().authenticated();// Our private endpoints


    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public AuthenticationEntryPointHandler authenticationEntryPointHandler(){
//        return new AuthenticationEntryPointHandler();
//    }
}
