package com.greenbeansapps.myschooltransportation.infra.repositories.schemas;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "payment_schema")
public class PaymentSchema implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false)
    private Date paymentDate;
    @Column(nullable = false)
    private Months paymentMonth;

    @Column(nullable = false)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public PaymentSchema() {}

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
