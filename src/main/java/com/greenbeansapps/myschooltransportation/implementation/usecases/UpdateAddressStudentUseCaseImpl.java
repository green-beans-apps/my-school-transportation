package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateAddressStudentUseCase;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateAddressRequest;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateAddressStudentUseCaseImpl implements UpdateAddressStudentUseCase {

    private final AddressRepository addressRepo;
    private final StudentRepository studentRepo;

    public UpdateAddressStudentUseCaseImpl(AddressRepository addressRepo, StudentRepository studentRepo) {
        this.addressRepo = addressRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public Address execute(UpdateAddressRequest data) {
        Optional<Student> getStudent = this.studentRepo.findById(data.studentId());
        if(getStudent.isEmpty()) {
            throw new StudentNotFoundException();
        }

        Optional<Address> getAddress = this.addressRepo.findById(getStudent.get().getAddress().getId());

        getAddress.get().setCity(data.city());
        getAddress.get().setDistrict(data.district());
        getAddress.get().setStreet(data.street());
        getAddress.get().setReferencePoint(data.referencePoint());
        getAddress.get().setHouseNumber(data.houseNumber());

        return addressRepo.updateAddress(getAddress.get());
    }
}
