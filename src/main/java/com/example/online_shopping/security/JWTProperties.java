package com.example.online_shopping.security;

public class JWTProperties {

    public static final String SECRET = "SecretKeyToGenJWTs";

    public static final long EXPIRATION_TIME = 864000000; // 10 days

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    public static final String USER_ROLE = "USER_ROLE";

    public static final String ROLE = "ROLE_";

    public static final String SIGN_UP_URL = "/sign_up";
}
