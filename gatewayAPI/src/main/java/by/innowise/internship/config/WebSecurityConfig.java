package by.innowise.internship.config;

import by.innowise.internship.security.JwtConfigurer;
import by.innowise.internship.security.JwtToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtToken jwtToken;
    private static final String REGISTRATION = "/api/registration";
    private static final String LOGIN = "/api/login";
    private static final String LOGOUT = "/api/logout";

    public WebSecurityConfig(JwtToken jwtToken) {
        this.jwtToken = jwtToken;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/v2/api-docs",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/spring-security-oauth-resource/**").permitAll()
                .antMatchers(HttpMethod.POST, REGISTRATION).permitAll()
                .antMatchers(HttpMethod.GET, LOGIN).permitAll()
                .antMatchers(HttpMethod.POST, LOGOUT).authenticated()
                .and()
                .apply(new JwtConfigurer(jwtToken));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}