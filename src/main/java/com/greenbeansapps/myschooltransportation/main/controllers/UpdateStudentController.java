package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.UpdateStudentRequest;
import com.greenbeansapps.myschooltransportation.implementation.usecases.UpdateStudentUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.constraints.ValidUUID;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class UpdateStudentController {

  @Autowired
  private UpdateStudentUseCaseImpl updateStudentUseCase;


  @PutMapping()
  public ResponseEntity updateStudent(@RequestBody @Valid UpdateStudentDtoRequest updateStudentDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      ErrorResponse errorResponse = new ErrorResponse();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    var updateStudent = this.updateStudentUseCase.execute(new UpdateStudentRequest(UUID.fromString(updateStudentDto.studentId), updateStudentDto.name, updateStudentDto.school, updateStudentDto.grade, updateStudentDto.transportationType, updateStudentDto.monthlyPayment, updateStudentDto.monthlyPaymentExpiration, updateStudentDto.shift));
    var responseUpdateStudent = new UpdateStudentResponseDto(updateStudent.getId(), updateStudent.getName(), updateStudent.getSchool(), updateStudent.getGrade(), updateStudent.getTransportationType().toString(), updateStudent.getMonthlyPayment(), updateStudent.getMonthlyPaymentExpiration(), updateStudent.getShift().toString());
    return ResponseEntity.status(HttpStatus.OK).body(responseUpdateStudent);
  }


  public record UpdateStudentDtoRequest(
          @ValidUUID
          String studentId,
          @NotBlank
          String name,
          @NotBlank
          String school,
          @NotBlank
          String grade,
          @NotBlank
          String transportationType,
          @NotNull
          Double monthlyPayment,
          @NotNull
          Integer monthlyPaymentExpiration,
          @NotBlank
          String shift
  ) {}

  public record UpdateStudentResponseDto(
          UUID studentId,
          String name,
          String school,
          String grade,
          String transportationType,
          Double monthlyPayment,
          Integer monthlyPaymentExpiration,
          String shift
  ) {}
}
