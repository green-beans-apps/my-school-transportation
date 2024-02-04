package com.greenbeansapps.myschooltransportation.infra.repositories.projection;

import com.greenbeansapps.myschooltransportation.domain.enums.Months;

import java.util.Date;
import java.util.UUID;

public class PaymentProjection {
    private UUID id;
    private Date paymentDate;
    private Months paymentMonth;

    public PaymentProjection(UUID id, Date paymentDate, Months paymentMonth) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.paymentMonth = paymentMonth;
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
}
