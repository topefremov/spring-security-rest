package io.github.efrem.springsecurityrest.security.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static io.github.efrem.springsecurityrest.security.SecurityConstraints.*;

@SuppressWarnings("rawtypes")
public class JWTCheckClaimsAuthenticationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws ServletException, IOException {
		Optional<String> token = Optional.empty();
		Cookie[] cookies = req.getCookies();
		if (cookies != null)
			token = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(X_TOKEN))
					.map(cookie -> cookie.getValue()).findFirst();

		if (token.isPresent()) {
			String tokenString = token.get();
			if (!tokenString.isEmpty()) {
				UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenString);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		}

		chain.doFilter(req, res);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if (token != null) {
			// parse the token.
			Claims claims = Jwts.parser().setSigningKey(SECRET.getBytes())
					.parseClaimsJws(token.replace(TOKEN_PREFIX, "")).getBody();
			String user = claims.getSubject();

			GrantedAuthority authority = getSimpleGrantedAuthority(claims.get(AUTHORITIES_CLAIM));

			if (user != null) {
				return new UsernamePasswordAuthenticationToken(user, null, Arrays.asList(authority));
			}
			return null;
		}
		return null;
	}

	private SimpleGrantedAuthority getSimpleGrantedAuthority(Object obj) {
		HashMap authorityClaim = ((HashMap) ((List) obj).get(0));
		String role = (String) authorityClaim.get("authority");
		return new SimpleGrantedAuthority(role);
	}

}
