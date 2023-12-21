package com.greenbeansapps.myschooltransportation.domain.entities;

import java.util.UUID;

public class Student {
    private UUID id;
    private String name;
    private String school;
    private String grade;
    private Integer monthlyPayment;
    private String monthlyPaymentExpiration;
    private Conductor conductor;
    private Responsible responsible;
    private Address address;

    public Student(UUID id, String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, Conductor conductor, Responsible responsible, Address address) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.grade = grade;
        this.monthlyPayment = monthlyPayment;
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
        this.conductor = conductor;
        this.responsible = responsible;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Integer monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public String getMonthlyPaymentExpiration() {
        return monthlyPaymentExpiration;
    }

    public void setMonthlyPaymentExpiration(String monthlyPaymentExpiration) {
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        this.responsible = responsible;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}