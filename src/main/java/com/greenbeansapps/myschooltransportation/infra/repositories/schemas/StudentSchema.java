package com.greenbeansapps.myschooltransportation.infra.repositories.schemas;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "student")
public class StudentSchema implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String school;
    @Column(nullable = false)
    private String grade;
    @Column(nullable = false)
    private TransportationType transportationType;
    @Column(nullable = false)
    private Shift shift;
    @Column(nullable = false)
    private Double monthlyPayment;
    @Column(nullable = false)
    private Integer monthlyPaymentExpiration;

    @ManyToOne
    @JoinColumn(name = "conductor_id", nullable = false)
    private ConductorSchema conductor;

    @ManyToOne
    @JoinColumn(name = "responsible_id", nullable = false)
    private ResponsibleSchema responsible;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressSchema address;

    public StudentSchema() {
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

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }

    public Integer getMonthlyPaymentExpiration() {
        return monthlyPaymentExpiration;
    }

    public void setMonthlyPaymentExpiration(Integer monthlyPaymentExpiration) {
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
    }

    public ConductorSchema getConductor() {
        return conductor;
    }

    public void setConductor(ConductorSchema conductor) {
        this.conductor = conductor;
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

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
