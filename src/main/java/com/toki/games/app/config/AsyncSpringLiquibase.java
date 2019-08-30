package com.toki.games.app.config;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.SQLException;

public class AsyncSpringLiquibase extends SpringLiquibase {
    public static final String DISABLED_MESSAGE = "Liquibase is disabled";
    public static final String STARTING_ASYNC_MESSAGE = "Starting Liquibase asynchronously, your database might not be ready at startup!";
    public static final String STARTING_SYNC_MESSAGE = "Starting Liquibase synchronously";
    public static final String STARTED_MESSAGE = "Liquibase has updated your database in {} ms";
    public static final String EXCEPTION_MESSAGE = "Liquibase could not start correctly, your database is NOT ready: {}";
    public static final long SLOWNESS_THRESHOLD = 5L;
    public static final String SLOWNESS_MESSAGE = "Warning, Liquibase took more than {} seconds to start up!";
    private final Logger logger = LoggerFactory.getLogger(AsyncSpringLiquibase.class);
    private final TaskExecutor taskExecutor;
    private final Environment env;

    public AsyncSpringLiquibase(@Qualifier("taskExecutor") TaskExecutor taskExecutor, Environment env) {
        this.taskExecutor = taskExecutor;
        this.env = env;
    }

    public void afterPropertiesSet() throws LiquibaseException {
        if (!this.env.acceptsProfiles(new String[]{"no-liquibase"})) {
            if (this.env.acceptsProfiles(new String[]{"dev", "heroku"})) {
                try {
                    Connection connection = this.getDataSource().getConnection();
                    Throwable var2 = null;

                    try {
                        this.taskExecutor.execute(() -> {
                            try {
                                this.logger.warn("Starting Liquibase asynchronously, your database might not be ready at startup!");
                                this.initDb();
                            } catch (LiquibaseException var3) {
                                this.logger.error("Liquibase could not start correctly, your database is NOT ready: {}", var3.getMessage(), var3);
                            }

                        });
                    } catch (Throwable var12) {
                        var2 = var12;
                        throw var12;
                    } finally {
                        if (connection != null) {
                            if (var2 != null) {
                                try {
                                    connection.close();
                                } catch (Throwable var11) {
                                    var2.addSuppressed(var11);
                                }
                            } else {
                                connection.close();
                            }
                        }

                    }
                } catch (SQLException var14) {
                    this.logger.error("Liquibase could not start correctly, your database is NOT ready: {}", var14.getMessage(), var14);
                }
            } else {
                this.logger.debug("Starting Liquibase synchronously");
                this.initDb();
            }
        } else {
            this.logger.debug("Liquibase is disabled");
        }

    }

    protected void initDb() throws LiquibaseException {
        StopWatch watch = new StopWatch();
        watch.start();
        super.afterPropertiesSet();
        watch.stop();
        this.logger.debug("Liquibase has updated your database in {} ms", watch.getTotalTimeMillis());
        if (watch.getTotalTimeMillis() > 5000L) {
            this.logger.warn("Warning, Liquibase took more than {} seconds to start up!", 5L);
        }

    }
}
