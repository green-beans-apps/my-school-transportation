package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Payment;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.usecases.CalculateContractTerminationUseCase;
import com.greenbeansapps.myschooltransportation.domain.utils.ArrayConverter;
import com.greenbeansapps.myschooltransportation.implementation.dto.CalculateContractTerminationDto;
import com.greenbeansapps.myschooltransportation.infra.repositories.IPaymentRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class CalculateContractTerminationUseCaseImpl implements CalculateContractTerminationUseCase {

    @Autowired
    private IStudentRepositoryJPA studentRepository;

    @Autowired
    private IPaymentRepositoryJPA paymentRepository;

    // Tem que criar um repositorio para a tabela de parametro de condutor, para pegar o percentual de maneira dinamica.

    @Override
    public CalculateContractTerminationDto execute(List<Student> students) {

        List<Float> monthlyValues = new ArrayList<>();
        List<Integer> monthsSinceYearStart = new ArrayList<>();
        List<Float> alreadyPaid = new ArrayList<>();
        Float percent = 0.35f; // Pegar esse valor de maneira dinamica da tabela de parametro condutor.

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

}
