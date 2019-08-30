package com.toki.games.app.web.rest.util;

public class PaginationInfo {
    private long total;
    private int limit;
    private int page;
    private String link;

    public PaginationInfo(long total, int limit, int page, String link) {
        this.total = total;
        this.limit = limit;
        this.page = page;
        this.link = link;
    }

    public PaginationInfo() {
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
