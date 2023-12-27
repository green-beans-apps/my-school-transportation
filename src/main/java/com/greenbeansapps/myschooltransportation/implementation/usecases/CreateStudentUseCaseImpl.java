package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.InvalidResponsibleException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;

import java.util.Optional;
import java.util.UUID;

public class CreateStudentUseCaseImpl implements CreateStudentUseCase {
    private final StudentRepository studentRepo;
    private final ResponsibleRepository responsibleRepo;
    private final AddressRepository addressRepo;

    public CreateStudentUseCaseImpl(StudentRepository studentRepo, ResponsibleRepository responsibleRepo, AddressRepository addressRepo) {
        this.studentRepo = studentRepo;
        this.responsibleRepo = responsibleRepo;
        this.addressRepo = addressRepo;
    }

    @Override
    public Student execute(String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, Conductor conductor, UUID responsibleId, UUID addressId) {
        // conductor virá junto com a autenticação
        // responsible e address será pego pela consulta no banco depois de ser criado.

        Optional<Responsible> responsibleExist = this.responsibleRepo.findById(responsibleId);
        Optional<Address> addressExist = this.addressRepo.findById(addressId);

        if (!responsibleExist.isPresent()) {
            throw new InvalidResponsibleException();
        }
        if (!addressExist.isPresent()) {
            throw new InvalidAddressException();
        }

        var newStudent = new Student(UUID.randomUUID(), name, school, grade, monthlyPayment, monthlyPaymentExpiration, conductor, responsibleExist.get(), addressExist.get());
        this.studentRepo.create(newStudent);
        return newStudent;
    }
}
