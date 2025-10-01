package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false)
    private Date paymentDate;
    @Column(nullable = false)
    private Months paymentMonth;
    @Column(nullable = false)
    private Integer paymentYear;


    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    public Payment() {
    }

    public Payment(UUID id, Date paymentDate, Months paymentMonth, Student student) {
        setId(id);
        setPaymentDate(paymentDate);
        setPaymentMonth(paymentMonth);
        setStudent(student);
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

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        if (paymentDate == null) {
            throw new IllegalArgumentException("Payment date cannot be null");
        }
        this.paymentDate = paymentDate;
    }

    public Months getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(Months paymentMonth) {
        if (paymentMonth == null) {
            throw new IllegalArgumentException("Payment month cannot be null");
        }
        boolean isValidMonth = false;
        for (Months month : Months.values()) {
            if (month == paymentMonth) {
                isValidMonth = true;
                break;
            }
        }
        if (!isValidMonth) {
            throw new IllegalArgumentException("Payment month must be a valid month");
        }
        this.paymentMonth = paymentMonth;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        this.student = student;
    }

    public Integer getPaymentYear() {
        return paymentYear;
    }

    public void setPaymentYear(Integer paymentYear) {
        this.paymentYear = paymentYear;
    }
}
