package com.toki.games.app.web.rest.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.toki.games.app.web.rest.errors.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.web.util.UriComponentsBuilder;

@JsonInclude(Include.NON_NULL)
public class Response {
    private Object data;
    private HttpStatus httpStatus;
    private Integer httpCode;
    private String exceptionMsg;
    private String exceptionCode;
    private PaginationInfo pagination;

    public Response(ExceptionCode exceptionCode) {
        super();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.exceptionCode = exceptionCode.getCode();
        this.exceptionMsg = exceptionCode.getMessage();
    }

    public Response(ExceptionCode exceptionCode, String message) {
        super();
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.exceptionCode = exceptionCode.getCode();
        this.exceptionMsg = message;
    }

    public Response(HttpStatus status, ExceptionCode exceptionCode) {
        super();
        this.httpStatus = status;
        this.exceptionCode = exceptionCode.getCode();
        this.exceptionMsg = exceptionCode.getMessage();
    }

    public Response(Object data) {
        super();
        this.data = data;
        this.httpStatus = HttpStatus.OK;
        this.httpCode = HttpCode.SUCCESS.getValue();
    }

    public Response() {
        super();
        this.httpStatus = HttpStatus.OK;
        this.httpCode = HttpCode.SUCCESS.getValue();
    }

    public Response(Page pageObject, String baseUrl) {
        super();
        if (pageObject != null) {
            this.data = pageObject.getContent();
            int page = pageObject.getNumber();
            int size = pageObject.getSize();
            long total = pageObject.getTotalElements();
            long totalPages = pageObject.getTotalPages();

            String link = "";
            if ((page + 1) < totalPages) {
                link = "<" + generateUri(baseUrl, page + 1, size) + ">; rel=\"next\",";
            }
            // prev link
            if ((page) > 0) {
                link += "<" + generateUri(baseUrl, page - 1, size) + ">; rel=\"prev\",";
            }
            // last and first link
            int lastPage = 0;
            if (totalPages > 0) {
                lastPage = (int)totalPages - 1;
            }
            link += "<" + generateUri(baseUrl, lastPage, size) + ">; rel=\"last\",";
            link += "<" + generateUri(baseUrl, 0, size) + ">; rel=\"first\"";
            this.pagination = new PaginationInfo(total, size, page, link);
        }
        this.httpStatus = HttpStatus.OK;
        this.httpCode = HttpCode.SUCCESS.getValue();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    public String getExceptionMsg() {
        return exceptionMsg;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public PaginationInfo getPagination() {
        return pagination;
    }

    public void setPagination(PaginationInfo pagination) {
        this.pagination = pagination;
    }

    private static String generateUri(String baseUrl, int page, int size) {
        return UriComponentsBuilder.fromUriString(baseUrl).queryParam("page", page).queryParam("size", size).toUriString();
    }
}
