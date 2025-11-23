package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.Months;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateMonthlyFeesUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.MonthlyFeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CreateMonthlyFeesUseCaseImpl implements CreateMonthlyFeesUseCase {

    private final MonthlyFeeRepository monthlyFeeRepo;

    public CreateMonthlyFeesUseCaseImpl(MonthlyFeeRepository monthlyFeeRepo) {
        this.monthlyFeeRepo = monthlyFeeRepo;
    }

    @Override
    public void execute(Student student) {
        // Criando mensalidades
        for (Months month : Months.values()) {

            MonthlyFee monthlyFee = new MonthlyFee();
            monthlyFee.setStudent(student);
            monthlyFee.setReferenceMonth(month);
            monthlyFee.setReferenceYear(String.valueOf(student.getRegistrationDate().getYear()));
            monthlyFee.setAmount(BigDecimal.valueOf(student.getMonthlyPayment()));

            this.monthlyFeeRepo.create(monthlyFee);
        }
    }
}
