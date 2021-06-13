package com.bkbwongo.core.ebaasa.security;

import com.bkbwongo.core.ebaasa.security.filters.JwtTokenVerifier;
import com.bkbwongo.core.ebaasa.security.filters.JwtUsernameAndPasswordAuthFilter;
import com.bkbwongo.core.ebaasa.security.model.EbaasaAuthenticationEntryPoint;
import com.bkbwongo.core.ebaasa.security.service.EbaasaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author bkaaron
 * @created on 26/04/2021
 * @project ebaasa-sms
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //enable resource authentication instead of antMatchers
public class EbaasaSecurityConfig extends WebSecurityConfigurerAdapter {

    private final EbaasaUserDetailsService ebaasaUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final EbaasaAuthenticationEntryPoint ebaasaAuthenticationEntryPoint;

    @Autowired
    public EbaasaSecurityConfig(EbaasaUserDetailsService ebaasaUserDetailsService,
                                PasswordEncoder passwordEncoder,
                                EbaasaAuthenticationEntryPoint ebaasaAuthenticationEntryPoint){
        this.passwordEncoder = passwordEncoder;
        this.ebaasaUserDetailsService = ebaasaUserDetailsService;
        this.ebaasaAuthenticationEntryPoint = ebaasaAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(ebaasaAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtUsernameAndPasswordAuthFilter(authenticationManager()))
                .addFilterAfter(new JwtTokenVerifier(), JwtUsernameAndPasswordAuthFilter.class)
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/token/**").permitAll()
                .antMatchers("swagger*").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        var provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(ebaasaUserDetailsService);

        return provider;
    }
}
