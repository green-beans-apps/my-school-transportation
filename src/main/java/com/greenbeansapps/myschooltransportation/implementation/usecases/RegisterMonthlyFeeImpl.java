package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.MonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.RegisterMonthlyFee;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterMonthlyFeeRequest;
import com.greenbeansapps.myschooltransportation.infra.repositories.IMonthlyFeeRepositoryJPA;
import com.greenbeansapps.myschooltransportation.infra.repositories.IStudentRepositoryJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterMonthlyFeeImpl implements RegisterMonthlyFee {

    @Autowired
    private IMonthlyFeeRepositoryJPA monthlyFeeRepository;

    @Autowired
    private IStudentRepositoryJPA studentRepository;

    @Override
    public void execute(RegisterMonthlyFeeRequest data) {
        MonthlyFee monthlyFee = new MonthlyFee();

        Student student = studentRepository.findById(data.studentId())
                .orElseThrow(StudentNotFoundException::new);

        monthlyFee.setStudent(student);
        monthlyFee.setReferenceMonth(data.referenceMonth());
        monthlyFee.setReferenceYear(data.referenceYear());
        monthlyFee.setAmount(data.amount());

        monthlyFeeRepository.save(monthlyFee);
    }
}
