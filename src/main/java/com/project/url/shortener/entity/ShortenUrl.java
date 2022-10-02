package com.project.url.shortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = DBFeilds.SHORT_URL_TABLE_NAME)
public class ShortenUrl {

    @Id
    @Column(name = DBFeilds.PROXY)
    private String proxy;

    @Column(name = DBFeilds.DESTINATION_URL)
    private String destinationUrl;

    @Column(name = DBFeilds.USER_EMAIL)
    private String userEmail;

    @Column(name = DBFeilds.IS_ALIAS)
    private Boolean isAlias;

    public String getProxy() {
        return proxy;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public String getDestinationUrl() {
        return destinationUrl;
    }

    public void setDestinationUrl(String destinationUrl) {
        this.destinationUrl = destinationUrl;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getIsAlias() {
        return isAlias;
    }

    public void setIsAlias(Boolean alias) {
        isAlias = alias;
    }

    @Override
    public String toString() {
        return "ShortenUrl{" +
                "proxy='" + proxy + '\'' +
                ", destinationUrl='" + destinationUrl + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
