package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.ParametersConductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.ParametersConductorNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CalculateContractTerminationUseCase;
import com.greenbeansapps.myschooltransportation.domain.utils.ArrayConverter;
import com.greenbeansapps.myschooltransportation.implementation.dto.CalculateContractTerminationDto;
import com.greenbeansapps.myschooltransportation.infra.opencl.CalculateContractTermination;
import com.greenbeansapps.myschooltransportation.infra.repositories.IParametersConductorJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CalculateContractTerminationUseCaseImpl implements CalculateContractTerminationUseCase {

    @Autowired
    private IPaymentRepositoryJPA paymentRepository;

    @Autowired
    private IParametersConductorJPA parametersConductorRepo;

    @Autowired
    private IStudentRepositoryJPA studentRepo;

    @Autowired
    private CalculateContractTermination calculateContractTermination;

    @Override
    public void execute(List<Student> students) {

        CalculateContractTerminationDto data = returnCalculateContractTerminationDto(students);

        float[] updatedTerminationAmount = calculateContractTermination.calculateContractTermination(
                data.getMonthlyValues(), data.getMonthsSinceYearStart(), data.getAlreadyPaid(), data.getPercent());

        List<Student> studentsUpdated = new ArrayList<>();
        for (int i = 0; i < students.size(); i++) {

           Student student = students.get(i);
           student.setContractTerminationValue(BigDecimal.valueOf(updatedTerminationAmount[i]));

           studentsUpdated.add(student);
        }

        studentRepo.saveAll(studentsUpdated);
    }

    public CalculateContractTerminationDto returnCalculateContractTerminationDto(List<Student> students) {

        List<Float> monthlyValues = new ArrayList<>();
        List<Integer> monthsSinceYearStart = new ArrayList<>();
        List<Float> alreadyPaid = new ArrayList<>();
        Float percent = getPercentContractTermination(students.get(0).getConductor());

        for (Student student : students) {
            LocalDate registrationDate = student.getRegistrationDate();
            LocalDate firstMonth = LocalDate.of(registrationDate.getYear(), 1, 1);

            int monthsDifference = (int) ChronoUnit.MONTHS.between(firstMonth, registrationDate);

            BigDecimal totalPaid = paymentRepository.findAllPaymentByStudentId(student.getId())
                    .stream()
                    .map(Payment::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            monthlyValues.add(student.getMonthlyPayment().floatValue());
            monthsSinceYearStart.add(monthsDifference);
            alreadyPaid.add(totalPaid.floatValue());
        }

        return new CalculateContractTerminationDto(
                ArrayConverter.toFloatArray(monthlyValues),
                ArrayConverter.toIntArray(monthsSinceYearStart),
                ArrayConverter.toFloatArray(alreadyPaid),
                percent);
    }

    private Float getPercentContractTermination(Conductor conductor) {

        Optional<ParametersConductor> parametersConductor = parametersConductorRepo.findById(conductor.getId());

        if (parametersConductor.isEmpty()) {
            throw new ParametersConductorNotFoundException();
        }

        return parametersConductor.get().getPercentContractTermination();
    }

}
