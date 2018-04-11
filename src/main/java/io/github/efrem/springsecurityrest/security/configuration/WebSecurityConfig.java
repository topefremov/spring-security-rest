package io.github.efrem.springsecurityrest.security.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import io.github.efrem.springsecurityrest.security.LogoutSuccessHandlerImpl;
import io.github.efrem.springsecurityrest.security.filters.JWTCheckClaimsAuthenticationFilter;
import io.github.efrem.springsecurityrest.security.filters.LoginPasswordJWTAuthenticationFilter;

import static io.github.efrem.springsecurityrest.security.SecurityConstraints.X_TOKEN;

@EnableWebSecurity
public class WebSecurityConfig {

	@Configuration
	public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

		private AuthenticationProvider adDaoAuthenticationProvider;

		@Autowired
		public ApiWebSecurityConfigurationAdapter(AuthenticationProvider adDaoAuthenticationProvider) {
			this.adDaoAuthenticationProvider = adDaoAuthenticationProvider;
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.cors().and().csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/login")
					.permitAll().anyRequest().fullyAuthenticated().and()
					.addFilterBefore(new JWTCheckClaimsAuthenticationFilter(), BasicAuthenticationFilter.class)
					.addFilter(new LoginPasswordJWTAuthenticationFilter(authenticationManager())).sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST")).deleteCookies(X_TOKEN)
					.logoutSuccessHandler(new LogoutSuccessHandlerImpl());
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) {
			auth.authenticationProvider(adDaoAuthenticationProvider);
		}
	}

	@Configuration
	@Order(1)
	public static class H2ConsoleSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.antMatcher("/h2-console/**").headers().disable().csrf().disable().cors();
		}
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		configuration.setAllowedHeaders(
				Arrays.asList("origin", "content-type", "accept", "authorization", "Etag", "if-none-match"));
		configuration.setExposedHeaders(
				Arrays.asList("origin", "content-type", "accept", "authorization", "Etag", "if-none-match"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
