package io.github.efrem.springsecurityrest.security;

public interface SecurityConstraints {
	public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String X_TOKEN = "X-TOKEN";
    public static final String AUTHORITIES_CLAIM = "authorities";
}
