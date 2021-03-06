package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    public UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2");
        web.ignoring().antMatchers("/h2/**");
        // TODO: If you are implementing the security requirements, remove line below
        // web.ignoring().antMatchers("/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/register","/items/**", "/logoutUser","/footer/**","/shopping-cart/**","/payments/**","/feedback").permitAll()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .failureUrl("/login?error=BadCredentials")
                .defaultSuccessUrl("/items", true)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/");
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //da imame eden harkodiran admin
        auth.inMemoryAuthentication()
                .withUser("admin").password(passwordEncoder.encode("admin"))
                .authorities("ROLE_ADMIN");
        auth.userDetailsService(this.userDetailsService);
    }

}
