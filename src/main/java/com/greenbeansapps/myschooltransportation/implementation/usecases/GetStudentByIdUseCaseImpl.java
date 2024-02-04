package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.dto.PaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionWithPaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.exceptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.domain.usecases.GetStudentByIdUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.PaymentRepository;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.StudentRepository;
import com.greenbeansapps.myschooltransportation.infra.repositories.projection.StudentProjection;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetStudentByIdUseCaseImpl implements GetStudentByIdUseCase {

  private final StudentRepository studentRepo;
  private final PaymentRepository paymentRepo;

  public GetStudentByIdUseCaseImpl(StudentRepository studentRepo, PaymentRepository paymentRepo) {
    this.studentRepo = studentRepo;
    this.paymentRepo = paymentRepo;
  }

  @Override
  public StudentProjectionWithPaymentProjectionDto execute(UUID studentId) {
    Optional<StudentProjectionDto> student = this.studentRepo.findStudentByIdWithoutConductor(studentId);
    if(student.isEmpty()) {
      throw new StudentNotFoundException();
    }

    List<StudentProjectionWithPaymentProjectionDto> studentProjectionWithPaymentProjectionDtoList = new ArrayList<>();

    List<PaymentProjectionDto> paymentProjectionDtoList = this.paymentRepo.findAllPaymentByStudentId(studentId);

    return new StudentProjectionWithPaymentProjectionDto(student.get().getId(), student.get().getName(), student.get().getSchool(),
            student.get().getGrade(), student.get().getTransportationType(), student.get().getShift(), student.get().getMonthlyPayment(),
            student.get().getMonthlyPaymentExpiration(), student.get().getResponsible(), student.get().getAddress(), paymentProjectionDtoList);
  }
}
