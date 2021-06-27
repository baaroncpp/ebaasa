package com.bkbwongo.core.ebaasa.security;

import com.bkbwongo.core.ebaasa.security.service.EbaasaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * @author bkaaron
 * @created on 26/06/2021
 * @project ebaasa-sms
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_URL_PATHS = new String[] {
            "**/termsofuse",
            "/v3/api-docs/**",
            "/swagger-ui.html**","/swagger-ui/**",
            "/configuration/security","/configuration/ui","/swagger-resources/**",
            "/v2/api-docs**", "/webjars/**" };

    @Autowired
    private EbaasaUserDetailsService ebaasaUserDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // A custom implementation of user details service backed by a database would be in order
    /*@Bean
    @Override
    public UserDetailsService userDetailsService(){
        return ebaasaUserDetailsService;
    }
*/
    /*@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers(SWAGGER_URL_PATHS).
                and()
                .ignoring().mvcMatchers(HttpMethod.OPTIONS,"/**");
    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED)
                )
                .and()
                .authorizeRequests()
                .mvcMatchers(SWAGGER_URL_PATHS).permitAll()
                .anyRequest()
                .authenticated()
                .and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(ebaasaUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
