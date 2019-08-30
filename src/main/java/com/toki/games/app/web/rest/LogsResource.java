package com.toki.games.app.web.rest;

import com.toki.games.app.web.rest.util.Response;
import com.toki.games.app.web.rest.vm.LoggerVM;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/management")
public class LogsResource {

    @GetMapping("/logs")
    @Timed
    public Response getList() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        return new Response(context.getLoggerList()
            .stream()
            .map(LoggerVM::new)
            .collect(Collectors.toList()));
    }

    @PutMapping("/logs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Timed
    public Response changeLevel(@RequestBody LoggerVM jsonLogger) {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        context.getLogger(jsonLogger.getName()).setLevel(Level.valueOf(jsonLogger.getLevel()));
        return new Response(true);
    }
}
