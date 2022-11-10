package com.esimed.quizz.security;

import com.esimed.quizz.services.IUserService;
import com.esimed.quizz.services.JwtService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final IUserService userService;

    private final JwtService jwtService;

    public SecurityConfiguration(IUserService userService, JwtService jwtService)
    {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/signup").permitAll()
                .antMatchers("/signin").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), userService, jwtService))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}