package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidMonthlyPaymentExpirationException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidShiftException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidTransportationTypeException;
import com.greenbeansapps.myschooltransportation.domain.utils.CapitalizeWords;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student")
public class Student implements Serializable {
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
    @Column(nullable = false)
    private BigDecimal contractTerminationValue;
    @Column(nullable = false)
    private LocalDate registrationDate;

    @ManyToOne
    @JoinColumn(name = "conductor_id", nullable = false)
    private Conductor conductor;

    @ManyToOne
    @JoinColumn(name = "responsible_id", nullable = false)
    private Responsible responsible;

    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Payment> payments;

    public Student() {
    }

    public Student(UUID id, String name, String school, String grade, String transportationType, Double monthlyPayment, Integer monthlyPaymentExpiration, String shift, Conductor conductor, Responsible responsible, Address address) {
        setId(id);
        setName(name);
        setSchool(school);
        setGrade(grade);
        setTransportationType(transportationType);
        setMonthlyPayment(monthlyPayment);
        setMonthlyPaymentExpiration(monthlyPaymentExpiration);
        setConductor(conductor);
        setResponsible(responsible);
        setAddress(address);
        setShift(shift);
        setRegistrationDate(LocalDate.now());
        setContractTerminationValue(BigDecimal.ZERO);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() < 3 ) {
            throw new IllegalArgumentException("The name must have more than 3 characters");
        }
        this.name = CapitalizeWords.execute(name);
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        if (school == null || school.isEmpty()) {
            throw new IllegalArgumentException("School cannot be null or empty");
        }
        this.school = CapitalizeWords.execute(school);
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        if (grade == null || grade.isEmpty()) {
            throw new IllegalArgumentException("Grade cannot be null or empty");
        }
        this.grade = grade;
    }

    public TransportationType getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        if (transportationType.equalsIgnoreCase("IDA E VOLTA") || transportationType.equals("IDA_E_VOLTA")) {
            this.transportationType = TransportationType.IDA_E_VOLTA;
        } else if (transportationType.equalsIgnoreCase("IDA") || transportationType.equals("IDA")) {
            this.transportationType = TransportationType.IDA;
        } else if (transportationType.equalsIgnoreCase("VOLTA") || transportationType.equals("VOLTA")) {
            this.transportationType = TransportationType.VOLTA;
        } else {
            throw new InvalidTransportationTypeException();
        }
    }

    public Double getMonthlyPayment() {
        return monthlyPayment;
    }

    public void setMonthlyPayment(Double monthlyPayment) {
        if (monthlyPayment == null) {
            throw new IllegalArgumentException("Monthly payment cannot be null");
        }
        this.monthlyPayment = Math.round(monthlyPayment * 100.0)/100.0;
    }

    public Integer getMonthlyPaymentExpiration() {
        return monthlyPaymentExpiration;
    }

    public void setMonthlyPaymentExpiration(Integer monthlyPaymentExpiration) {
        if (monthlyPaymentExpiration == null) {
            throw new IllegalArgumentException("Monthly payment expiration cannot be null or empty");
        }
        if (monthlyPaymentExpiration < 1 || monthlyPaymentExpiration > 28) {
            throw new InvalidMonthlyPaymentExpirationException();
        }
        this.monthlyPaymentExpiration = monthlyPaymentExpiration;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        if (conductor == null) {
            throw new IllegalArgumentException("Conductor cannot be null");
        }
        this.conductor = conductor;
    }

    public Responsible getResponsible() {
        return responsible;
    }

    public void setResponsible(Responsible responsible) {
        if (responsible == null) {
            throw new IllegalArgumentException("Responsible cannot be null");
        }
        this.responsible = responsible;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        if (address == null) {
            throw new IllegalArgumentException("Address cannot be null");
        }
        this.address = address;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(String shift){
        if (shift == null || shift.isEmpty()) {
            throw new IllegalArgumentException("Shift cannot be null or empty");
        }

        if (shift.equalsIgnoreCase("MANHA") || shift.equals("MANHA")) {
            this.shift = Shift.MANHA;
        } else if (shift.equalsIgnoreCase("TARDE") || shift.equals("TARDE")) {
            this.shift = Shift.TARDE;
        }  else {
            throw new InvalidShiftException();
        }
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void setTransportationType(TransportationType transportationType) {
        this.transportationType = transportationType;
    }

    public BigDecimal getContractTerminationValue() {
        return contractTerminationValue;
    }

    public void setContractTerminationValue(BigDecimal contractTerminationValue) {
        this.contractTerminationValue = contractTerminationValue;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}