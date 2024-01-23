package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidAddressException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidConductorException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidResponsibleException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateStudentUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.AddressRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ResponsibleRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CreateStudentUseCaseImpl implements CreateStudentUseCase {
    private final StudentRepository studentRepo;
    private final ResponsibleRepository responsibleRepo;
    private final AddressRepository addressRepo;
    private final ConductorRepository conductorRepo;

    public CreateStudentUseCaseImpl(StudentRepository studentRepo, ResponsibleRepository responsibleRepo, AddressRepository addressRepo, ConductorRepository conductorRepo) {
        this.studentRepo = studentRepo;
        this.responsibleRepo = responsibleRepo;
        this.addressRepo = addressRepo;
        this.conductorRepo = conductorRepo;
    }

    @Override
    public Student execute(String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, UUID conductorId, UUID responsibleId, UUID addressId) {
        // conductor virá junto com a autenticação
        // responsible e address será pego pela consulta no banco depois de ser criado.

        Optional<Responsible> responsible = this.responsibleRepo.findById(responsibleId);
        Optional<Address> address = this.addressRepo.findById(addressId);
        Optional<Conductor> conductor = this.conductorRepo.findById(conductorId);

        if (responsible.isEmpty()) {
            throw new InvalidResponsibleException();
        }
        if (address.isEmpty()) {
            throw new InvalidAddressException();
        }
        if (conductor.isEmpty()) {
            throw new InvalidConductorException();
        }

        var newStudent = new Student(UUID.randomUUID(), name, school, grade, monthlyPayment, monthlyPaymentExpiration, conductor.get(), responsible.get(), address.get());
        this.studentRepo.create(newStudent);
        return newStudent;
    }
}
