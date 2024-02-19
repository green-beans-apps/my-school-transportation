package com.greenbeansapps.myschooltransportation.domain.dto;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.enums.Shift;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;

import java.util.List;
import java.util.UUID;

public class StudentProjectionWithPaymentProjectionDto extends StudentProjectionDto{
    private List<PaymentProjectionDto> payments;

    public StudentProjectionWithPaymentProjectionDto(UUID id, String name, String school, String grade, TransportationType transportationType, Shift shift, Double monthlyPayment, Integer monthlyPaymentExpiration, Responsible responsible, Address address, List<PaymentProjectionDto> payments) {
        super(id, name, school, grade, transportationType, shift, monthlyPayment, monthlyPaymentExpiration, responsible, address);
        this.payments = payments;
    }

    public List<PaymentProjectionDto> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentProjectionDto> payments) {
        this.payments = payments;
    }
}
