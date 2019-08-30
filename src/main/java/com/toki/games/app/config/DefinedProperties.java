package com.toki.games.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(
    prefix = "tokigames",
    ignoreUnknownFields = false
)
@Component
public class DefinedProperties {

    private final DefinedProperties.Async async = new DefinedProperties.Async();
    private final DefinedProperties.Http http = new DefinedProperties.Http();
    private final DefinedProperties.Cache cache = new DefinedProperties.Cache();
    private final DefinedProperties.Mail mail = new DefinedProperties.Mail();
    private final DefinedProperties.Security security = new DefinedProperties.Security();
    private final DefinedProperties.Swagger swagger = new DefinedProperties.Swagger();
    private final DefinedProperties.Metrics metrics = new DefinedProperties.Metrics();
    private final DefinedProperties.Logging logging = new DefinedProperties.Logging();
    private final CorsConfiguration cors = new CorsConfiguration();
    private final DefinedProperties.Social social = new DefinedProperties.Social();
    private final DefinedProperties.Gateway gateway = new DefinedProperties.Gateway();
    private final DefinedProperties.Registry registry = new DefinedProperties.Registry();
    private final DefinedProperties.Url url = new DefinedProperties.Url();

    public DefinedProperties() {
    }

    public DefinedProperties.Async getAsync() {
        return this.async;
    }

    public DefinedProperties.Http getHttp() {
        return this.http;
    }

    public DefinedProperties.Cache getCache() {
        return this.cache;
    }

    public DefinedProperties.Mail getMail() {
        return this.mail;
    }

    public DefinedProperties.Registry getRegistry() {
        return this.registry;
    }

    public DefinedProperties.Security getSecurity() {
        return this.security;
    }

    public DefinedProperties.Swagger getSwagger() {
        return this.swagger;
    }

    public DefinedProperties.Metrics getMetrics() {
        return this.metrics;
    }

    public DefinedProperties.Logging getLogging() {
        return this.logging;
    }

    public CorsConfiguration getCors() {
        return this.cors;
    }

    public DefinedProperties.Social getSocial() {
        return this.social;
    }

    public DefinedProperties.Gateway getGateway() {
        return this.gateway;
    }

    public DefinedProperties.Url getUrl(){return this.url;}

    public static class Registry {
        private String password;

        public Registry() {
            this.password = ConfigDefaults.Registry.password;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class Gateway {
        private final DefinedProperties.Gateway.RateLimiting rateLimiting = new DefinedProperties.Gateway.RateLimiting();
        private Map<String, List<String>> authorizedMicroservicesEndpoints;

        public Gateway() {
            this.authorizedMicroservicesEndpoints = ConfigDefaults.Gateway.authorizedMicroservicesEndpoints;
        }

        public DefinedProperties.Gateway.RateLimiting getRateLimiting() {
            return this.rateLimiting;
        }

        public Map<String, List<String>> getAuthorizedMicroservicesEndpoints() {
            return this.authorizedMicroservicesEndpoints;
        }

        public void setAuthorizedMicroservicesEndpoints(Map<String, List<String>> authorizedMicroservicesEndpoints) {
            this.authorizedMicroservicesEndpoints = authorizedMicroservicesEndpoints;
        }

        public static class RateLimiting {
            private boolean enabled = false;
            private long limit = 100000L;
            private int durationInSeconds = 3600;

            public RateLimiting() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public long getLimit() {
                return this.limit;
            }

            public void setLimit(long limit) {
                this.limit = limit;
            }

            public int getDurationInSeconds() {
                return this.durationInSeconds;
            }

            public void setDurationInSeconds(int durationInSeconds) {
                this.durationInSeconds = durationInSeconds;
            }
        }
    }

    public static class Social {
        private String redirectAfterSignIn = "/#/home";

        public Social() {
        }

        public String getRedirectAfterSignIn() {
            return this.redirectAfterSignIn;
        }

        public void setRedirectAfterSignIn(String redirectAfterSignIn) {
            this.redirectAfterSignIn = redirectAfterSignIn;
        }
    }

    public static class Logging {
        private final DefinedProperties.Logging.Logstash logstash = new DefinedProperties.Logging.Logstash();

        public Logging() {
        }

        public DefinedProperties.Logging.Logstash getLogstash() {
            return this.logstash;
        }

        public static class Logstash {
            private boolean enabled = false;
            private String host = "localhost";
            private int port = 5000;
            private int queueSize = 512;

            public Logstash() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getHost() {
                return this.host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return this.port;
            }

            public void setPort(int port) {
                this.port = port;
            }

            public int getQueueSize() {
                return this.queueSize;
            }

            public void setQueueSize(int queueSize) {
                this.queueSize = queueSize;
            }
        }
    }

    public static class Metrics {
        private final DefinedProperties.Metrics.Jmx jmx = new DefinedProperties.Metrics.Jmx();
        private final DefinedProperties.Metrics.Logs logs = new DefinedProperties.Metrics.Logs();
        private final DefinedProperties.Metrics.Prometheus prometheus = new DefinedProperties.Metrics.Prometheus();

        public Metrics() {
        }

        public DefinedProperties.Metrics.Jmx getJmx() {
            return this.jmx;
        }

        public DefinedProperties.Metrics.Logs getLogs() {
            return this.logs;
        }

        public DefinedProperties.Metrics.Prometheus getPrometheus() {
            return this.prometheus;
        }

        public static class Prometheus {
            private boolean enabled = false;
            private String endpoint = "/prometheusMetrics";

            public Prometheus() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getEndpoint() {
                return this.endpoint;
            }

            public void setEndpoint(String endpoint) {
                this.endpoint = endpoint;
            }
        }

        public static class Logs {
            private boolean enabled = false;
            private long reportFrequency = 60L;

            public Logs() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public long getReportFrequency() {
                return this.reportFrequency;
            }

            public void setReportFrequency(long reportFrequency) {
                this.reportFrequency = reportFrequency;
            }
        }

        public static class Jmx {
            private boolean enabled = true;

            public Jmx() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }
        }
    }

    public static class Swagger {
        private String title = "Application API";
        private String description = "API documentation";
        private String version = "0.0.1";
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
        private String defaultIncludePattern;
        private String host;
        private String[] protocols;
        private boolean useDefaultResponseMessages;

        public Swagger() {
            this.termsOfServiceUrl = ConfigDefaults.Swagger.termsOfServiceUrl;
            this.contactName = ConfigDefaults.Swagger.contactName;
            this.contactUrl = ConfigDefaults.Swagger.contactUrl;
            this.contactEmail = ConfigDefaults.Swagger.contactEmail;
            this.license = ConfigDefaults.Swagger.license;
            this.licenseUrl = ConfigDefaults.Swagger.licenseUrl;
            this.defaultIncludePattern = "/api/.*";
            this.host = ConfigDefaults.Swagger.host;
            this.protocols = ConfigDefaults.Swagger.protocols;
            this.useDefaultResponseMessages = true;
        }

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return this.description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getTermsOfServiceUrl() {
            return this.termsOfServiceUrl;
        }

        public void setTermsOfServiceUrl(String termsOfServiceUrl) {
            this.termsOfServiceUrl = termsOfServiceUrl;
        }

        public String getContactName() {
            return this.contactName;
        }

        public void setContactName(String contactName) {
            this.contactName = contactName;
        }

        public String getContactUrl() {
            return this.contactUrl;
        }

        public void setContactUrl(String contactUrl) {
            this.contactUrl = contactUrl;
        }

        public String getContactEmail() {
            return this.contactEmail;
        }

        public void setContactEmail(String contactEmail) {
            this.contactEmail = contactEmail;
        }

        public String getLicense() {
            return this.license;
        }

        public void setLicense(String license) {
            this.license = license;
        }

        public String getLicenseUrl() {
            return this.licenseUrl;
        }

        public void setLicenseUrl(String licenseUrl) {
            this.licenseUrl = licenseUrl;
        }

        public String getDefaultIncludePattern() {
            return this.defaultIncludePattern;
        }

        public void setDefaultIncludePattern(String defaultIncludePattern) {
            this.defaultIncludePattern = defaultIncludePattern;
        }

        public String getHost() {
            return this.host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String[] getProtocols() {
            return this.protocols;
        }

        public void setProtocols(String[] protocols) {
            this.protocols = protocols;
        }

        public boolean isUseDefaultResponseMessages() {
            return this.useDefaultResponseMessages;
        }

        public void setUseDefaultResponseMessages(boolean useDefaultResponseMessages) {
            this.useDefaultResponseMessages = useDefaultResponseMessages;
        }
    }

    public static class Security {
        private final DefinedProperties.Security.ClientAuthorization clientAuthorization = new DefinedProperties.Security.ClientAuthorization();
        private final DefinedProperties.Security.Authentication authentication = new DefinedProperties.Security.Authentication();
        private final DefinedProperties.Security.RememberMe rememberMe = new DefinedProperties.Security.RememberMe();

        public Security() {
        }

        public DefinedProperties.Security.ClientAuthorization getClientAuthorization() {
            return this.clientAuthorization;
        }

        public DefinedProperties.Security.Authentication getAuthentication() {
            return this.authentication;
        }

        public DefinedProperties.Security.RememberMe getRememberMe() {
            return this.rememberMe;
        }

        public static class RememberMe {
            @NotNull
            private String key;

            public RememberMe() {
                this.key = ConfigDefaults.Security.RememberMe.key;
            }

            public String getKey() {
                return this.key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public static class Authentication {
            private final DefinedProperties.Security.Authentication.Jwt jwt = new DefinedProperties.Security.Authentication.Jwt();

            public Authentication() {
            }

            public DefinedProperties.Security.Authentication.Jwt getJwt() {
                return this.jwt;
            }

            public static class Jwt {
                private String secret;
                private String base64Secret;
                private long tokenValidityInSeconds;
                private long tokenValidityInSecondsForRememberMe;

                public Jwt() {
                    this.secret = ConfigDefaults.Security.Authentication.Jwt.secret;
                    this.base64Secret = ConfigDefaults.Security.Authentication.Jwt.base64Secret;
                    this.tokenValidityInSeconds = 1800L;
                    this.tokenValidityInSecondsForRememberMe = 2592000L;
                }

                public String getSecret() {
                    return this.secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
                }

                public String getBase64Secret() {
                    return this.base64Secret;
                }

                public void setBase64Secret(String base64Secret) {
                    this.base64Secret = base64Secret;
                }

                public long getTokenValidityInSeconds() {
                    return this.tokenValidityInSeconds;
                }

                public void setTokenValidityInSeconds(long tokenValidityInSeconds) {
                    this.tokenValidityInSeconds = tokenValidityInSeconds;
                }

                public long getTokenValidityInSecondsForRememberMe() {
                    return this.tokenValidityInSecondsForRememberMe;
                }

                public void setTokenValidityInSecondsForRememberMe(long tokenValidityInSecondsForRememberMe) {
                    this.tokenValidityInSecondsForRememberMe = tokenValidityInSecondsForRememberMe;
                }
            }
        }

        public static class ClientAuthorization {
            private String accessTokenUri;
            private String tokenServiceId;
            private String clientId;
            private String clientSecret;

            public ClientAuthorization() {
                this.accessTokenUri = ConfigDefaults.Security.ClientAuthorization.accessTokenUri;
                this.tokenServiceId = ConfigDefaults.Security.ClientAuthorization.tokenServiceId;
                this.clientId = ConfigDefaults.Security.ClientAuthorization.clientId;
                this.clientSecret = ConfigDefaults.Security.ClientAuthorization.clientSecret;
            }

            public String getAccessTokenUri() {
                return this.accessTokenUri;
            }

            public void setAccessTokenUri(String accessTokenUri) {
                this.accessTokenUri = accessTokenUri;
            }

            public String getTokenServiceId() {
                return this.tokenServiceId;
            }

            public void setTokenServiceId(String tokenServiceId) {
                this.tokenServiceId = tokenServiceId;
            }

            public String getClientId() {
                return this.clientId;
            }

            public void setClientId(String clientId) {
                this.clientId = clientId;
            }

            public String getClientSecret() {
                return this.clientSecret;
            }

            public void setClientSecret(String clientSecret) {
                this.clientSecret = clientSecret;
            }
        }

    }

    public static class Url {
        private String cheapUrl;
        private String businessUrl;

        public Url() {
        }

        public Url(String cheapUrl, String businessUrl) {
            this.cheapUrl = cheapUrl;
            this.businessUrl = businessUrl;
        }

        public String getCheapUrl() {
            return cheapUrl;
        }

        public void setCheapUrl(String cheapUrl) {
            this.cheapUrl = cheapUrl;
        }

        public String getBusinessUrl() {
            return businessUrl;
        }

        public void setBusinessUrl(String businessUrl) {
            this.businessUrl = businessUrl;
        }
    }

    public static class Mail {
        private boolean enabled = false;
        private String from = "";
        private String baseUrl = "";

        public Mail() {
        }

        public boolean isEnabled() {
            return this.enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getFrom() {
            return this.from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getBaseUrl() {
            return this.baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }
    }

    public static class Cache {
        private final DefinedProperties.Cache.Hazelcast hazelcast = new DefinedProperties.Cache.Hazelcast();
        private final DefinedProperties.Cache.Ehcache ehcache = new DefinedProperties.Cache.Ehcache();
        private final DefinedProperties.Cache.Infinispan infinispan = new DefinedProperties.Cache.Infinispan();
        private final DefinedProperties.Cache.Memcached memcached = new DefinedProperties.Cache.Memcached();

        public Cache() {
        }

        public DefinedProperties.Cache.Hazelcast getHazelcast() {
            return this.hazelcast;
        }

        public DefinedProperties.Cache.Ehcache getEhcache() {
            return this.ehcache;
        }

        public DefinedProperties.Cache.Infinispan getInfinispan() {
            return this.infinispan;
        }

        public DefinedProperties.Cache.Memcached getMemcached() {
            return this.memcached;
        }

        public static class Memcached {
            private boolean enabled = false;
            private String servers = "localhost:11211";
            private int expiration = 300;
            private boolean useBinaryProtocol = true;

            public Memcached() {
            }

            public boolean isEnabled() {
                return this.enabled;
            }

            public void setEnabled(boolean enabled) {
                this.enabled = enabled;
            }

            public String getServers() {
                return this.servers;
            }

            public void setServers(String servers) {
                this.servers = servers;
            }

            public int getExpiration() {
                return this.expiration;
            }

            public void setExpiration(int expiration) {
                this.expiration = expiration;
            }

            public boolean isUseBinaryProtocol() {
                return this.useBinaryProtocol;
            }

            public void setUseBinaryProtocol(boolean useBinaryProtocol) {
                this.useBinaryProtocol = useBinaryProtocol;
            }
        }

        public static class Infinispan {
            private final DefinedProperties.Cache.Infinispan.Local local = new DefinedProperties.Cache.Infinispan.Local();
            private final DefinedProperties.Cache.Infinispan.Distributed distributed = new DefinedProperties.Cache.Infinispan.Distributed();
            private final DefinedProperties.Cache.Infinispan.Replicated replicated = new DefinedProperties.Cache.Infinispan.Replicated();
            private String configFile = "default-configs/default-jgroups-tcp.xml";
            private boolean statsEnabled = false;

            public Infinispan() {
            }

            public String getConfigFile() {
                return this.configFile;
            }

            public void setConfigFile(String configFile) {
                this.configFile = configFile;
            }

            public boolean isStatsEnabled() {
                return this.statsEnabled;
            }

            public void setStatsEnabled(boolean statsEnabled) {
                this.statsEnabled = statsEnabled;
            }

            public DefinedProperties.Cache.Infinispan.Local getLocal() {
                return this.local;
            }

            public DefinedProperties.Cache.Infinispan.Distributed getDistributed() {
                return this.distributed;
            }

            public DefinedProperties.Cache.Infinispan.Replicated getReplicated() {
                return this.replicated;
            }

            public static class Replicated {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;

                public Replicated() {
                }

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }
            }

            public static class Distributed {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;
                private int instanceCount = 1;

                public Distributed() {
                }

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }

                public int getInstanceCount() {
                    return this.instanceCount;
                }

                public void setInstanceCount(int instanceCount) {
                    this.instanceCount = instanceCount;
                }
            }

            public static class Local {
                private long timeToLiveSeconds = 60L;
                private long maxEntries = 100L;

                public Local() {
                }

                public long getTimeToLiveSeconds() {
                    return this.timeToLiveSeconds;
                }

                public void setTimeToLiveSeconds(long timeToLiveSeconds) {
                    this.timeToLiveSeconds = timeToLiveSeconds;
                }

                public long getMaxEntries() {
                    return this.maxEntries;
                }

                public void setMaxEntries(long maxEntries) {
                    this.maxEntries = maxEntries;
                }
            }
        }

        public static class Ehcache {
            private int timeToLiveSeconds = 3600;
            private long maxEntries = 100L;

            public Ehcache() {
            }

            public int getTimeToLiveSeconds() {
                return this.timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public long getMaxEntries() {
                return this.maxEntries;
            }

            public void setMaxEntries(long maxEntries) {
                this.maxEntries = maxEntries;
            }
        }

        public static class Hazelcast {
            private final DefinedProperties.Cache.Hazelcast.ManagementCenter managementCenter = new DefinedProperties.Cache.Hazelcast.ManagementCenter();
            private int timeToLiveSeconds = 3600;
            private int backupCount = 1;

            public Hazelcast() {
            }

            public DefinedProperties.Cache.Hazelcast.ManagementCenter getManagementCenter() {
                return this.managementCenter;
            }

            public int getTimeToLiveSeconds() {
                return this.timeToLiveSeconds;
            }

            public void setTimeToLiveSeconds(int timeToLiveSeconds) {
                this.timeToLiveSeconds = timeToLiveSeconds;
            }

            public int getBackupCount() {
                return this.backupCount;
            }

            public void setBackupCount(int backupCount) {
                this.backupCount = backupCount;
            }

            public static class ManagementCenter {
                private boolean enabled = false;
                private int updateInterval = 3;
                private String url = "";

                public ManagementCenter() {
                }

                public boolean isEnabled() {
                    return this.enabled;
                }

                public void setEnabled(boolean enabled) {
                    this.enabled = enabled;
                }

                public int getUpdateInterval() {
                    return this.updateInterval;
                }

                public void setUpdateInterval(int updateInterval) {
                    this.updateInterval = updateInterval;
                }

                public String getUrl() {
                    return this.url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }
            }
        }
    }

    public static class Http {
        private final DefinedProperties.Http.Cache cache = new DefinedProperties.Http.Cache();
        public DefinedProperties.Http.Version version;
        private boolean useUndertowUserCipherSuitesOrder = true;

        public Http() {
            this.version = ConfigDefaults.Http.version;
        }

        public DefinedProperties.Http.Cache getCache() {
            return this.cache;
        }

        public DefinedProperties.Http.Version getVersion() {
            return this.version;
        }

        public void setVersion(DefinedProperties.Http.Version version) {
            this.version = version;
        }

        public boolean isUseUndertowUserCipherSuitesOrder() {
            return this.useUndertowUserCipherSuitesOrder;
        }

        public void setUseUndertowUserCipherSuitesOrder(boolean useUndertowUserCipherSuitesOrder) {
            this.useUndertowUserCipherSuitesOrder = useUndertowUserCipherSuitesOrder;
        }

        public static enum Version {
            V_1_1,
            V_2_0;

            private Version() {
            }
        }

        public static class Cache {
            private int timeToLiveInDays = 1461;

            public Cache() {
            }

            public int getTimeToLiveInDays() {
                return this.timeToLiveInDays;
            }

            public void setTimeToLiveInDays(int timeToLiveInDays) {
                this.timeToLiveInDays = timeToLiveInDays;
            }
        }
    }

    public static class Async {
        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 10000;

        public Async() {
        }

        public int getCorePoolSize() {
            return this.corePoolSize;
        }

        public void setCorePoolSize(int corePoolSize) {
            this.corePoolSize = corePoolSize;
        }

        public int getMaxPoolSize() {
            return this.maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }

        public int getQueueCapacity() {
            return this.queueCapacity;
        }

        public void setQueueCapacity(int queueCapacity) {
            this.queueCapacity = queueCapacity;
        }
    }
}
