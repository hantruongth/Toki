package com.toki.games.app.web.rest.util;

import com.toki.games.app.web.rest.errors.ExceptionCode;

import java.util.Optional;

public interface ResponseUtil {
    static Response wrapOrNotFound(Optional maybeResponse) {
        return (Response) maybeResponse.map((response) -> {
            return new Response(response);
        }).orElse(new Response(ExceptionCode.NOT_FOUND));
    }
}
