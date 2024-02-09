package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private UUID id;
    private String paymentDate;
    private Months paymentMonth;
    private Student student;

    public Payment() {
    }

    public Payment(UUID id, String paymentDate, Months paymentMonth, Student student) {
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

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
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
}
