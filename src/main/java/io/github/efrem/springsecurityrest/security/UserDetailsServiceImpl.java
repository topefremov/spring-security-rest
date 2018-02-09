package io.github.efrem.springsecurityrest.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.github.efrem.springsecurityrest.domain.ApplicationUser;
import io.github.efrem.springsecurityrest.repository.ApplicationUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ApplicationUserRepository applicationUserRepository;

	@Autowired
	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.applicationUserRepository = applicationUserRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.findByLogin(login);
		if (applicationUser == null)
			throw new UsernameNotFoundException(login);
		
		String password = applicationUser.getPassword();
		
		if (applicationUser.getType().equals("LDAP"))
			password = "DumbPassword";
		
		return new CustomUser(applicationUser.getLogin(), password, applicationUser.getType(),
				Arrays.asList(new SimpleGrantedAuthority("ROLE_" + applicationUser.getRole())));
	}

}
