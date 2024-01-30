package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;

import java.util.UUID;

public interface CreateStudentUseCase {
    public Student execute(String name, String school, String grade, String transportationType, Integer monthlyPayment, String monthlyPaymentExpiration, UUID conductorId, UUID responsibleId, UUID addressId);
}
