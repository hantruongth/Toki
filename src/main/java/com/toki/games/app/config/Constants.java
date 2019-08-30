package com.toki.games.app.config;

public final class Constants {

    public static final String LOGIN_REGEX = "^[_.@A-Za-z0-9-]*$";

    public static final String SYSTEM_ACCOUNT = "system";
    public static final String ANONYMOUS_USER = "anonymoususer";
    public static final String DEFAULT_LANGUAGE = "en";

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    public static final String SPRING_PROFILE_HEROKU = "heroku";
    public static final String SPRING_PROFILE_AWS_ECS = "aws-ecs";
    public static final String SPRING_PROFILE_SWAGGER = "swagger";
    public static final String SPRING_PROFILE_NO_LIQUIBASE = "no-liquibase";
    public static final String SPRING_PROFILE_K8S = "k8s";
    public static final int FLIGHT_SEARCH_LIMIT = 100;
    public static final int NUMBER_OF_THREAD_HANDLE_API_SEARCH = 10;
    public static final String DEFAULT_SORT_FIELD = "departureTime";
    public static final String DEFAULT_SORT_DIRECTION = "asc";

    private Constants() {
    }
}
