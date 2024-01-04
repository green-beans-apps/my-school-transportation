package com.greenbeansapps.myschooltransportation.infra.repositories.schemas;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "student_schema")
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
    private Integer monthlyPayment;
    @Column(nullable = false)
    private String monthlyPaymentExpiration;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "conductor_id")
    private Conductor conductor;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "responsible_id")
    private Responsible responsible;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

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
