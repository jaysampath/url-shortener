package com.project.url.shortener.rest.request;

public class ShoretenUrlRequest {

    private String destinationUrl;

    private String userEmail;

    private Boolean isAlias;

    private String alias;

    public Boolean getIsAlias() {
        return isAlias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setIsAlias(Boolean alias) {
        isAlias = alias;
    }

    public String getAlias(){
        return alias;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }
}
