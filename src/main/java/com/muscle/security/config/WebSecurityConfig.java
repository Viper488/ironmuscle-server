package com.muscle.security.config;

import com.muscle.user.filters.UserAuthenticationFilter;
import com.muscle.user.filters.UserAuthorizationFilter;
import com.muscle.user.service.UserService;
import com.muscle.user.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    private final JwtUtil jwtUtil;
    private final String USER = "USER";
    private final String EMPLOYEE = "EMPLOYEE";
    private final String ADMIN = "ADMIN";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UserAuthenticationFilter userAuthenticationFilter = new UserAuthenticationFilter(authenticationManagerBean(), jwtUtil);
        userAuthenticationFilter.setFilterProcessesUrl("/api/v*/login");

        http.csrf().disable();
        http.authorizeRequests().antMatchers("/api/v*/registration",
                "/api/v*/registration/confirm*",
                "/api/v*/password/reset*",
                "/api/v*/login/**"
        ).permitAll();

        http.authorizeRequests().antMatchers("/api/v*/password/reset?token=*&password=*").permitAll();
        http.authorizeRequests().antMatchers("/api/v*/welcome").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/myself").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/token/refresh/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/password/change").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/users*").hasAnyAuthority(EMPLOYEE, ADMIN);


        http.authorizeRequests().antMatchers("/api/v*/training/template", "/api/v*/training/*").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/training", "/api/v*/training/*/exercises").hasAnyAuthority(EMPLOYEE, ADMIN);

        http.authorizeRequests().antMatchers("/api/v*/exercise").hasAnyAuthority(EMPLOYEE, ADMIN);

        http.authorizeRequests().antMatchers("/api/v*/request", "/api/v*/request/user").hasAnyAuthority(USER);
        http.authorizeRequests().antMatchers("/api/v*/request/*", "/api/v*/request/*/comment", "/api/v*/request/*/comments").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/request/employee", "/api/v*/request/all").hasAnyAuthority( EMPLOYEE);

        http.authorizeRequests().antMatchers("/api/v*/user/training/*").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/trainings").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/trainings/**").hasAnyAuthority(EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/history/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/points/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/ranking/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/ranking/list/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);
        http.authorizeRequests().antMatchers("/api/v*/user/badges/**").hasAnyAuthority(USER, EMPLOYEE, ADMIN);

        http.authorizeRequests().anyRequest().authenticated().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(userAuthenticationFilter);
        http.addFilterBefore(new UserAuthorizationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
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
