package com.muscle.security.config;

import com.muscle.user.filters.JwtRequestFilter;
import com.muscle.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtRequestFilter jwtRequestFilter;
    private final String USER = "USER";
    private final String EMPLOYEE = "EMPLOYEE";
    private final String ADMIN = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v*/registration",
                "/api/v*/registration/confirm*",
                "/api/v*/authenticate",
                "/api/v*/system/authenticate",
                "/api/v*/password/reset*"
        ).permitAll();

        http.authorizeRequests().antMatchers("/api/v*/password/reset?token=*&password=*").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/welcome").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/myself").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/password/change").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/users*").hasAnyAuthority(EMPLOYEE, ADMIN);


        http.authorizeRequests().antMatchers("/api/v*/training/template", "/api/v*/training/*").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/training", "/api/v*/training/*/exercises").hasAnyAuthority(EMPLOYEE, ADMIN);

        http.authorizeRequests().antMatchers("/api/v*/exercise").hasAnyAuthority(EMPLOYEE, ADMIN);

        http.authorizeRequests().antMatchers("/api/v*/request", "/api/v*/request/user").hasAnyAuthority(USER);
        http.authorizeRequests().antMatchers("/api/v*/request/*", "/api/v*/request/*/comment", "/api/v*/request/*/comments").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/request/employee").hasAnyAuthority( EMPLOYEE);

        http.authorizeRequests().antMatchers("/api/v*/user/trainings").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/trainings/*").hasAnyAuthority(EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/trainings/history*").hasAnyAuthority(USER, EMPLOYEE, ADMIN);

        http.authorizeRequests().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider());
    }

    @Bean
    public DaoAuthenticationProvider provider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
