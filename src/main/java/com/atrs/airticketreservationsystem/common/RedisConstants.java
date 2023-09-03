package com.atrs.airticketreservationsystem.common;

public class RedisConstants {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final String LOGIN_VERIFICATION_KEY = "login:verification:";

    public static final Long LOGIN_CODE_TTL = 2L;
    public static final Long LOGIN_VERIFICATION_TTL = 1L;
    public static final Long FLIGHT_TTL = 30L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 30L;
    public static final String FLIGHT_MSG = "flight:";
    public static final String ORDER_MSG = "order:";

}
