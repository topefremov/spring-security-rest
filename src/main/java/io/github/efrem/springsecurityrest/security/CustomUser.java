package io.github.efrem.springsecurityrest.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUser extends User {

	private final String type;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3735311409795182404L;

	public CustomUser(String username, String password, String type,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
