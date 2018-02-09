package io.github.efrem.springsecurityrest.security;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AdDaoAuthenticationProvider extends DaoAuthenticationProvider {

	private Hashtable<String, Object> env = new Hashtable<>();
	private final String providerUrl;
	private final String realm;

	public AdDaoAuthenticationProvider(String providerUrl, String realm) {
		super();
		this.providerUrl = providerUrl;
		this.realm = realm;
	}

	protected void doAfterPropertiesSet() throws Exception {
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "DIGEST-MD5");
		env.put("java.naming.security.sasl.realm", realm);
		super.doAfterPropertiesSet();
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		CustomUser customUser = (CustomUser) userDetails;

		if (customUser.getType().equals("LDAP")) {
			adAdditionalAuthenticationChecks(userDetails, authentication);
		} else {
			super.additionalAuthenticationChecks(userDetails, authentication);
		}
	}

	private void adAdditionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) {
		if (authentication.getCredentials() == null) {
			logger.debug("Authentication failed: no credentials provided");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}

		String presentedPassword = authentication.getCredentials().toString();
		env.put(Context.SECURITY_PRINCIPAL, userDetails.getUsername());
		env.put(Context.SECURITY_CREDENTIALS, presentedPassword);

		try {

			DirContext ctx = new InitialDirContext(env);
			logger.info("Active Directory user successfully authenticated");
			ctx.close();
		} catch (NamingException e) {
			logger.debug("Authentication failed: Active Directory user could not authenticate successfully");
			throw new BadCredentialsException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
		}
	}

}
