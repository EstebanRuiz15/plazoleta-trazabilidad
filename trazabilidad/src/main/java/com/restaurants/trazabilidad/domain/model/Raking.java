package com.restaurants.trazabilidad.domain.model;

import java.time.Duration;

public class Raking {
    private Integer employeId;
    private String employeEmail;
    private String averageTime;

    public Raking() {
    }

    public Raking(Integer employeId, String employeEmail, String averageTime) {
        this.employeId = employeId;
        this.employeEmail = employeEmail;
        this.averageTime = averageTime;
    }

    public Integer getEmployeId() {
        return employeId;
    }

    public void setEmployeId(Integer employeId) {
        this.employeId = employeId;
    }

    public String getEmployeEmail() {
        return employeEmail;
    }

    public void setEmployeEmail(String employeEmail) {
        this.employeEmail = employeEmail;
    }

    public String getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(String averageTime) {
        this.averageTime = averageTime;
    }
}
