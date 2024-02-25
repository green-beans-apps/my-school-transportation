package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateAddressRequest;

import java.util.UUID;

public interface UpdateAddressStudentUseCase {
    public Address execute(UpdateAddressRequest data);
}
