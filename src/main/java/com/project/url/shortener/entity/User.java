package com.project.url.shortener.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = DBFeilds.USER_TABLE_NAME)
public class User {

    @Id
    @Column(name = DBFeilds.EMAIL)
    private String email;

    @Column(name = DBFeilds.USERNAME)
    private String username;

    @Column(name = DBFeilds.PRETTY_NAME)
    private String prettyName;

    @Column(name = DBFeilds.PASSWORD)
    private String password;

    @Column(name = DBFeilds.ROLE)
    private String role;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPrettyName() {
        return prettyName;
    }

    public void setPrettyName(String prettyName) {
        this.prettyName = prettyName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
