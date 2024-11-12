package com.restaurants.trazabilidad.domain.model;

import java.util.Date;

public class User {
    private Integer id;
    private String name;

    private String lastName;

    private Integer document;

    private String celPhone;

    private Date birthDay;

    private String email;

    private String password;
    private String rol;
    private Integer rest_id;

    public User( ) {
    }

    public User(String name, String lastName, Integer document, String celPhone, Date birthDay, String email, String password, String rol, Integer rest_id) {
        this.name = name;
        this.lastName = lastName;
        this.document = document;
        this.celPhone = celPhone;
        this.birthDay = birthDay;
        this.email = email;
        this.password = password;
        this.rol = rol;
        this.rest_id = rest_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDocument() {
        return document;
    }

    public void setDocument(Integer document) {
        this.document = document;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Integer getRest_id() {
        return rest_id;
    }

    public void setRest_id(Integer rest_id) {
        this.rest_id = rest_id;
    }
}
