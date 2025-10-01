package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.enums.ChargeType;
import com.greenbeansapps.myschooltransportation.domain.enums.PaymentType;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "revenue_summary")
public class RevenueSummary implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    // Período de Referência
    @Column(nullable = false, name = "reference_month")
    private Integer referenceMonth;

    @Column(nullable = false, name = "reference_year")
    private Integer referenceYear;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount; // Valor original

    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount; // desconto

    @Column(name = "late_fee", precision = 10, scale = 2)
    private BigDecimal lateFee; // Multa por atraso

    @Column(name = "interest", precision = 10, scale = 2)
    private BigDecimal interest; // Juros

    @Column(name = "final_amount", precision = 10, scale = 2)
    private BigDecimal finalAmount; // Valor final (amount - discount + lateFee + interest)

    // Tipos
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "payment_type", length = 30)
    private PaymentType paymentType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "charge_type", length = 30)
    private ChargeType chargeType;

    // Datas
    @Column(name = "due_date")
    private LocalDate dueDate; // Data de vencimento

    @Column(name = "payment_date")
    private LocalDate paymentDate; // Data que foi pago

    @Column(name = "created_at")
    private LocalDateTime createdAt; // Quando o registro foi criado

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // ultima alteracao

    // Informações Adicionais
    @Column(name = "days_overdue")
    private Integer daysOverdue; // Dias de atraso

//    @Column(name = "invoice_number", length = 50)
//    private String invoiceNumber; // numero do recibo


    @Column(name = "receipt_url", length = 255)
    private String receiptUrl; // URL do comprovante


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (id == null) {
            id = UUID.randomUUID();
        }
        calculateFinalAmount();
        calculateDaysOverdue();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
        calculateFinalAmount();
        calculateDaysOverdue();
    }

    // calculo valor final
    private void calculateFinalAmount() {
        BigDecimal base = amount != null ? amount : BigDecimal.ZERO;
        BigDecimal disc = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal fee = lateFee != null ? lateFee : BigDecimal.ZERO;
        BigDecimal inter = interest != null ? interest : BigDecimal.ZERO;

        finalAmount = base.subtract(disc).add(fee).add(inter);
    }

    // calcular dias de atraso
    private void calculateDaysOverdue() {
        if (dueDate != null) {
            LocalDate today = LocalDate.now();
            if (today.isAfter(dueDate)) {
                daysOverdue = (int) java.time.temporal.ChronoUnit.DAYS.between(dueDate, today);
            } else {
                daysOverdue = 0;
            }
        }
    }
}
