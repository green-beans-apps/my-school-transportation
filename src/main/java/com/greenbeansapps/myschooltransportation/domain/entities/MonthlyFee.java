package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "monthly_fee")
public class MonthlyFee implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(nullable = false,name = "reference_month")
    private Months referenceMonth;

    @Column(nullable = false,name = "reference_year")
    private String referenceYear;

    @Column(nullable = false)
    private BigDecimal amount;

    public MonthlyFee() {
    }

    public MonthlyFee(UUID id, Student student, Months referenceMonth, String referenceYear, BigDecimal amount) {
        this.id = id;
        this.student = student;
        this.referenceMonth = referenceMonth;
        this.referenceYear = referenceYear;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Months getReferenceMonth() {
        return referenceMonth;
    }

    public void setReferenceMonth(Months referenceMonth) {
        this.referenceMonth = referenceMonth;
    }

    public String getReferenceYear() {
        return referenceYear;
    }

    public void setReferenceYear(String referenceYear) {
        this.referenceYear = referenceYear;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

