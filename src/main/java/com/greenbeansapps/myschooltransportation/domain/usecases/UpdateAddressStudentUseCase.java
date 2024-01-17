package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.UUID;

public interface UpdateAddressStudentUseCase {
    public Address execute(UUID studentId,  String city, String district, String street, String referencePoint, Integer houseNumber);
}
