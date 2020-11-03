package com.example.ms.sample.apigatewayzuul.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class ZuulWebSecurity extends WebSecurityConfigurerAdapter {

    @Value("${api.login.url.path}")
    private String loginUrlPath;

    @Value("${api.registration.url.path}")
    private String registrationUrlPath;

    @Value("${api.h2console.url.path}")
    private String h2ConsoleUrlPath;

    @Value("${api.actuator.url.path}")
    private String actuatorUrlPath;

    @Value("${api.users.actuator.url.path}")
    private String usersServiceActuatorUrlPath;

    @Value("${authorization.token.header.name}")
    private String authorizationTokenHeaderName;

    @Value("${authorization.token.header.prefix}")
    private String authorizationTokenHeaderPrefix;

    @Value("${token.secret.value}")
    private String tokenSecret;

    @Autowired
    private Environment environment;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthorizationPropertyHolder authorizationPropertyHolder = AuthorizationPropertyHolder.builder()
                .authorizationTokenHeaderName(authorizationTokenHeaderName)
                .authorizationTokenHeaderPrefix(authorizationTokenHeaderPrefix)
                .tokenSecret(tokenSecret).build();
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilter(new AuthorizationFilter(authenticationManager(), authorizationPropertyHolder))
                ;
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity
                .ignoring()
                .antMatchers(h2ConsoleUrlPath)
                .antMatchers(actuatorUrlPath)
                .antMatchers(usersServiceActuatorUrlPath)
                .antMatchers(HttpMethod.POST, loginUrlPath)
                .antMatchers(HttpMethod.POST, registrationUrlPath);

    }


}
