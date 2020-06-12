package com.giraone.soap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@SuppressWarnings("unused")
@Configuration
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private static final Logger log = LoggerFactory.getLogger(ApplicationProperties.class);

    private boolean showConfigOnStartup = true;
    private String soapEndpointUrl = "http://localhost:8080/ws/bank";
    private String defaultUri = "http://www.giraone.com/blz-service/";
    private String contextPath = "com.giraone.soap.wsdl";

    @PostConstruct
    private void startup() {
        if (this.showConfigOnStartup) {
            log.info(this.toString());
        }
    }

    public boolean isShowConfigOnStartup() {
        return showConfigOnStartup;
    }

    public void setShowConfigOnStartup(boolean showConfigOnStartup) {
        this.showConfigOnStartup = showConfigOnStartup;
    }

    public String getSoapEndpointUrl() {
        return soapEndpointUrl;
    }

    public void setSoapEndpointUrl(String soapEndpointUrl) {
        this.soapEndpointUrl = soapEndpointUrl;
    }

    public String getDefaultUri() {
        return defaultUri;
    }

    public void setDefaultUri(String defaultUri) {
        this.defaultUri = defaultUri;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
            "showConfigOnStartup=" + showConfigOnStartup +
            ", soapEndpointUrl='" + soapEndpointUrl + '\'' +
            ", defaultUri='" + defaultUri + '\'' +
            ", contextPath='" + contextPath + '\'' +
            '}';
    }
}
