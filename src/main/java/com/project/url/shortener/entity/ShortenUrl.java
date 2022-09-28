package com.project.url.shortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shortenUrls")
public class ShortenUrl {

    @Id
    @Column(name = "proxy")
    private String proxy;

    @Column(name = "destination")
    private String destinationUrl;

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

    @Override
    public String toString() {
        return "ShortenUrl{" +
                "proxyUrl='" + proxy + '\'' +
                ", destinationUrl='" + destinationUrl + '\'' +
                '}';
    }

}
