package com.example.the_bugtracker_mark_2.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;


@Configuration
@EnableWebSecurity


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService(){
        return new CustomUserDetails();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //CUSTOM CONFIGURATION
        http.


                cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://10.128.32.201:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                })
                .and().csrf().disable()
                .httpBasic().and()

                .authorizeRequests()
                .antMatchers("/activity/**",
                                        "/platforms/**",
                                        "/bug/pendingapprovals",
                                        "/bug/edit/**",
                                        "/bug/reassignment/**",
                                        "/users/**",
                                        "/roles/**",
                                        "/bug/pendingapprovals/**").hasAuthority("Admin")



                .antMatchers().hasAnyAuthority("Admin", "Developers")


                .antMatchers("/bug/**").hasAnyAuthority("Admin",  "Developer", "User")



                .antMatchers("/api/v1/activity/**",

                                        "/api/v1/bug/updatebug/",
                                        "/api/v1/bug/pendingapprovals",
                                        "/api/v1/bug/platforms/new",
                                        "/api/v1/bug/platforms/save",
                                        "/api/v1/bug/platforms/",
                                        "/api/v1/bug/roles/**",
                                        "/api/v1/bug/delete/")
                                        .hasAuthority("Admin")

                .antMatchers()
                                        .hasAuthority("Admin")


                .antMatchers("/api/v1/bug/search",
                                        "/api/v1/platforms/platformsundertreatment",
                                        "/api/v1/bug/justsearching",
                                        "/api/v1/platforms/deactivatedplatforms",
                                        "/api/v1/platforms/deactivatedplatforms",
                                        "/api/v1/bug/findusersonrole",
                                        "/api/v1/bug/activityonbug/**",
                                        "/api/v1/users/**")
                                        .hasAnyAuthority("Admin", "Developer")
                .antMatchers("api/v1/teams/**").permitAll()

                .antMatchers(HttpMethod.POST,"/api/v1/usersignup/save").permitAll()

                .antMatchers("/api/v1/usersignin/checkifuserexists").permitAll()
                .antMatchers("/api/v1/bug/**").permitAll()
                .antMatchers("/api/v1/bug").permitAll()
                .antMatchers("/api/v1/platforms/**").hasAnyAuthority("Admin", "Developer", "User")
                .antMatchers(HttpMethod.POST,"/api/v1/usersignin/login").permitAll()
                .antMatchers(HttpMethod.POST,"/usersignin/login").permitAll()



                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }

    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }




}
