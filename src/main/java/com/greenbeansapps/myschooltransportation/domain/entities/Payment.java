package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.Date;
import java.util.UUID;

public class Payment {
    private UUID id;
    private Date paymentDate;
    private Months paymentMonth;
    private Student student;

    public Payment(UUID id, Date paymentDate, Months paymentMonth, Student student) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.paymentMonth = paymentMonth;
        this.student = student;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Months getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(Months paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
