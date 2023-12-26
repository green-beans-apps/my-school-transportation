package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;

public interface CreateStudentUseCase {
    public Student execute(String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, Conductor conductor, Responsible responsible, Address address);
}
