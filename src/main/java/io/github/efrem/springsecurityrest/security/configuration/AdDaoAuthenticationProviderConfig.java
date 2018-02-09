package io.github.efrem.springsecurityrest.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import io.github.efrem.springsecurityrest.security.AdDaoAuthenticationProvider;

@Configuration
public class AdDaoAuthenticationProviderConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Bean
	AuthenticationProvider adDaoAuthenticationProvider(@Value("${ad.provider.url}") String providerUrl,
			@Value("${ad.realm}") String realm) {
		AdDaoAuthenticationProvider adDaoAuthenticationProvider = new AdDaoAuthenticationProvider(providerUrl, realm);
		adDaoAuthenticationProvider.setUserDetailsService(userDetailsService);
		adDaoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
		return adDaoAuthenticationProvider;
	}
}
