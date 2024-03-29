package com.greenbeansapps.myschooltransportation.infra.repositories.projection;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.AddressSchema;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ResponsibleSchema;

import java.util.UUID;

public class StudentProjection {
    private UUID id;
    private String name;
    private String school;
    private String grade;
    private TransportationType transportationType;
    private Shift shift;
    private Integer monthlyPayment;
    private Integer monthlyPaymentExpiration;
    private ResponsibleSchema responsible;
    private AddressSchema address;

    public StudentProjection(UUID id, String name, String school, String grade, TransportationType transportationType, Shift shift, Integer monthlyPayment, Integer monthlyPaymentExpiration, ResponsibleSchema responsible, AddressSchema address) {
        this.id = id;
        this.name = name;
        this.school = school;
        this.grade = grade;
        this.transportationType = transportationType;
        this.shift = shift;
        this.monthlyPayment = monthlyPayment;
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
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

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public Integer getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Integer monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Integer getMonthlyPaymentExpiration() {
        return monthlyPaymentExpiration;
    }

    public void setMonthlyPaymentExpiration(Integer monthlyPaymentExpiration) {
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
    }

    public ResponsibleSchema getResponsible() {
        return responsible;
    }

    public void setResponsible(ResponsibleSchema responsible) {
        this.responsible = responsible;
    }

    public AddressSchema getAddress() {
        return address;
    }

    public void setAddress(AddressSchema address) {
        this.address = address;
    }
}
