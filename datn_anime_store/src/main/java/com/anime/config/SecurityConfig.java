package com.anime.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

import com.anime.custom.UserDetailsServiceCustom;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean 
    public UserDetailsService userDetailsService() {
		return new UserDetailsServiceCustom();
	}
	
	@Bean 
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors().disable();
		
		http.authorizeHttpRequests()
			.antMatchers("/checkout", "/my-order").authenticated()
			.antMatchers("/anime/admin/index").hasAnyRole("ADMIN", "STAFF")
			.anyRequest()
			.permitAll();
		
		http.authenticationProvider(daoAuthenticationProvider());
		
		http.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/login")
			.failureUrl("/login/error");
		
		http.formLogin()
			.successHandler((req, resp, auth) ->{
				if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN") || r.getAuthority().equals("ROLE_STAFF"))) {
					resp.sendRedirect("/anime/admin/index");
				} else {
					resp.sendRedirect("/anime/index");
				}
			});
		
		http.rememberMe().tokenValiditySeconds(30*24*60*60);
		
		http.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/index")
			.addLogoutHandler(new SecurityContextLogoutHandler());
        return http.build();
    }
}
