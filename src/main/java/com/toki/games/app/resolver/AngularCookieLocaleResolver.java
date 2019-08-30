package com.toki.games.app.resolver;

import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.TimeZone;

public class AngularCookieLocaleResolver extends CookieLocaleResolver {
    public static final String QUOTE = "%22";

    public AngularCookieLocaleResolver() {
    }

    public Locale resolveLocale(HttpServletRequest request) {
        this.parseLocaleCookieIfNecessary(request);
        return (Locale)request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME);
    }

    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        this.parseLocaleCookieIfNecessary(request);
        return new TimeZoneAwareLocaleContext() {
            public Locale getLocale() {
                return (Locale)request.getAttribute(CookieLocaleResolver.LOCALE_REQUEST_ATTRIBUTE_NAME);
            }

            public TimeZone getTimeZone() {
                return (TimeZone)request.getAttribute(CookieLocaleResolver.TIME_ZONE_REQUEST_ATTRIBUTE_NAME);
            }
        };
    }

    public void addCookie(HttpServletResponse response, String cookieValue) {
        super.addCookie(response, this.quote(cookieValue));
    }

    private void parseLocaleCookieIfNecessary(HttpServletRequest request) {
        if (request.getAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME) == null) {
            Cookie cookie = WebUtils.getCookie(request, this.getCookieName());
            Locale locale = null;
            TimeZone timeZone = null;
            if (cookie != null) {
                String value = cookie.getValue();
                value = StringUtils.replace(value, "%22", "");
                String localePart = value;
                String timeZonePart = null;
                int spaceIndex = value.indexOf(32);
                if (spaceIndex != -1) {
                    localePart = value.substring(0, spaceIndex);
                    timeZonePart = value.substring(spaceIndex + 1);
                }

                locale = !"-".equals(localePart) ? StringUtils.parseLocaleString(localePart.replace('-', '_')) : null;
                if (timeZonePart != null) {
                    timeZone = StringUtils.parseTimeZoneString(timeZonePart);
                }

                if (this.logger.isTraceEnabled()) {
                    this.logger.trace("Parsed cookie value [" + cookie.getValue() + "] into locale '" + locale + "'" + (timeZone != null ? " and time zone '" + timeZone.getID() + "'" : ""));
                }
            }

            request.setAttribute(LOCALE_REQUEST_ATTRIBUTE_NAME, locale != null ? locale : this.determineDefaultLocale(request));
            request.setAttribute(TIME_ZONE_REQUEST_ATTRIBUTE_NAME, timeZone != null ? timeZone : this.determineDefaultTimeZone(request));
        }

    }

    String quote(String string) {
        return "%22" + string + "%22";
    }
}
