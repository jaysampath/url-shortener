package com.project.url.shortener;

public class Endpoints {

    //auth endoints
    private static final String AUTH_PREFIX = "/auth";
    public static final String USER_REGISTER = AUTH_PREFIX + "/register";
    public static final String USER_LOGIN = AUTH_PREFIX + "/login";
    public static final String TOKEN_VALIDATE = AUTH_PREFIX + "/validate";
    public static final String UPDATE_PASSWORD = AUTH_PREFIX + "/update" ;

    // user specific endpoints
    private static final String USER_PREFIX = "/user";
    public static final String SHORTEN_URL = USER_PREFIX + "/shorten";
    public static final String VALIDATE_ALIAS = USER_PREFIX + "/validate/alias";
    public static final String LIST_USER_URLS =  USER_PREFIX + "/urls/list";

    //ADMIN endpints
    private static final String ADMIN_PREFIX = "/admin";
    public static final String LIST_USERS = ADMIN_PREFIX+ "/list/users";
    public static final String LIST_PROXIES = ADMIN_PREFIX + "/list/proxy";
}
