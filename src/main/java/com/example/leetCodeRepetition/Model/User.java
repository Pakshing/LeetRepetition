package com.example.leetCodeRepetition.Model;

import jakarta.persistence.*;

import java.sql.Date;

@Table(name = "\"user\"")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "date_created", nullable = false)
    private Date dateCreated;

    @Column(name = "last_active", nullable = false)
    private Date last_active;

    @Column(name = "login_method", nullable = false)
    private String login_method;


    public User() {
        this.dateCreated = new Date(System.currentTimeMillis());
        this.last_active = new Date(System.currentTimeMillis());
    }

    public User(String email) {
        this.email = email;
        this.login_method = "LocalStorage";
        this.dateCreated = new Date(System.currentTimeMillis());
        this.last_active = new Date(System.currentTimeMillis());
    }

    public User(String email, String login_method) {
        this.email = email;
        this.login_method = login_method;
        this.dateCreated = new Date(System.currentTimeMillis());
        this.last_active = new Date(System.currentTimeMillis());
    }


    public Integer getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Date getLastActive() {
        return last_active;
    }

    public String getLogin_method() {
        return login_method;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastActive(Date last_active) {
        this.last_active = last_active;
    }

    public void setLogin_method(String login_method) {
        this.login_method = login_method;
    }

    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", dateCreated=" + dateCreated +
                ", last_active=" + last_active +
                ", login_method=" + login_method +
                '}';
    }



}

