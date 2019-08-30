package com.toki.games.app.filter;

import com.toki.games.app.config.DefinedProperties;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CachingHttpHeadersFilter implements Filter {
    public static final int DEFAULT_DAYS_TO_LIVE = 1461;
    public static final long DEFAULT_SECONDS_TO_LIVE;
    public static final long LAST_MODIFIED;
    private long cacheTimeToLive;
    private DefinedProperties definedProperties;

    public CachingHttpHeadersFilter(DefinedProperties definedProperties) {
        this.cacheTimeToLive = DEFAULT_SECONDS_TO_LIVE;
        this.definedProperties = definedProperties;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.cacheTimeToLive = TimeUnit.DAYS.toMillis((long)this.definedProperties.getHttp().getCache().getTimeToLiveInDays());
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        httpResponse.setHeader("Cache-Control", "max-age=" + this.cacheTimeToLive + ", public");
        httpResponse.setHeader("Pragma", "cache");
        httpResponse.setDateHeader("Expires", this.cacheTimeToLive + System.currentTimeMillis());
        httpResponse.setDateHeader("Last-Modified", LAST_MODIFIED);
        chain.doFilter(request, response);
    }

    static {
        DEFAULT_SECONDS_TO_LIVE = TimeUnit.DAYS.toMillis(1461L);
        LAST_MODIFIED = System.currentTimeMillis();
    }
}
