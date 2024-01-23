package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.UpdateAddressStudentUseCase;
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
    public Address execute(UUID studentId, String city, String district, String street, String referencePoint, Integer houseNumber) {
        Optional<Student> getStudent = this.studentRepo.findById(studentId);
        if(getStudent.isEmpty()) {
            throw new StudentNotFoundException();
        }

        Optional<Address> getAddress = this.addressRepo.findById(getStudent.get().getAddress().getId());

        getAddress.get().setCity(city);
        getAddress.get().setDistrict(district);
        getAddress.get().setStreet(street);
        getAddress.get().setReferencePoint(referencePoint);
        getAddress.get().setHouseNumber(houseNumber);

        return addressRepo.updateAddress(getAddress.get());
    }
}
