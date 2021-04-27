package com.esgi.flexges.constants;

public class SecurityConstants {
    public static final String SECRET = "pwlcCpXzGKeupvaNDIkE3fvVPU61qrw13r70J52IfzYzlj29pd47d1uXvVBKXP8";
    public static final long EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000; // a week of authentification
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/sign-up";
}