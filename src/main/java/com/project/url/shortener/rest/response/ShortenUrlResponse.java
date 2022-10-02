package com.project.url.shortener.rest.response;

public class ShortenUrlResponse {

    private String proxy;
    private String destinationUrl;
    private String userEmail;
    private Boolean isAlias;

    public String getProxy() {
        return proxy;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }
    public Boolean getIsAlias() {
        return isAlias;
    }

    public void setProxy(String proxy) {
        this.proxy =  proxy;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setIsAlias(Boolean alias) {
        isAlias = alias;
    }
}
