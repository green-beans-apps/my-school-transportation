package com.greenbeansapps.myschooltransportation.domain.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateAddressRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateResponsibleRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateStudentRequest;

import java.util.UUID;

public interface CreateStudentWithAddressAndResponsible {
    public Student execute(CreateStudentRequest studentData, CreateAddressRequest addressData, CreateResponsibleRequest responsibleData);

}
