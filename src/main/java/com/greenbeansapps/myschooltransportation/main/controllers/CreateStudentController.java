package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;
import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateAddressRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateResponsibleRequest;
import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.CreateStudentRequest;
import com.greenbeansapps.myschooltransportation.implementation.services.CreateStudentWithAddressAndResponsibleImpl;
import com.greenbeansapps.myschooltransportation.main.constraints.ValidUUID;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class CreateStudentController {

  @Autowired
  private CreateStudentWithAddressAndResponsibleImpl createStudentUseCase;

  @PostMapping("")
  public ResponseEntity create(@RequestBody @Valid CreateStudentDto data, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      ErrorResponse errorResponse = new ErrorResponse();
      for (FieldError fieldError : bindingResult.getFieldErrors()) {
        errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
      }
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    this.createStudentUseCase.execute(
            new CreateStudentRequest(
                    data.student.id(),
                    data.student.studentName(),
                    data.student.school(),
                    data.student.grade(),
                    data.student.transportationType(),
                    data.student.monthlyPayment(),
                    data.student.monthlyPaymentExpiration(),
                    data.student.shift(),
                    UUID.fromString(data.student.conductorId()),
                    data.responsible.id(),
                    data.address.id()
            ),
            new CreateAddressRequest(
                    data.address.id(),
                    data.address.city(),
                    data.address.district(),
                    data.address.street(),
                    data.address.referencePoint(),
                    data.address.houseNumber()
            ),
            new CreateResponsibleRequest(
                    data.responsible.id(),
                    data.responsible.responsibleName(),
                    data.responsible.email(),
                    data.responsible.phone()
            )
    );
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }


  public record CreateStudentDto(
          @Valid StudentDataDto student,
          @Valid ResponsibleDataDto responsible,
          @Valid AddressDataDto address
  ) { }

  public record StudentDataDto(
          UUID id,
          @NotBlank String studentName,
          @NotBlank String school,
          @NotBlank String grade,
          @NotBlank String transportationType,
          @NotNull Double monthlyPayment,
          @NotNull Integer monthlyPaymentExpiration,
          @NotBlank String shift,
          @ValidUUID
          String conductorId
  ) { }

  public record ResponsibleDataDto(
          UUID id,
          @NotBlank String responsibleName,
          String email,
          @NotBlank String phone
  ) { }

  public record AddressDataDto(
          UUID id,
          @NotBlank String city,
          @NotBlank String district,
          @NotBlank String street,
          String referencePoint,
          String houseNumber
  ) { }
}
