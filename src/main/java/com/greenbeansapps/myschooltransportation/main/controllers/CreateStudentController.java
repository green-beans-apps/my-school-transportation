package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.services.CreateStudentWithAddressAndResponsible;
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

    this.createStudentUseCase.execute(this.convertToRequest(data));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  private CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest convertToRequest(CreateStudentDto data) {
    var studentData = new CreateStudentWithAddressAndResponsible.StudentData(
            data.student().studentName(),
            data.student().school(),
            data.student().grade(),
            data.student().monthlyPayment(),
            data.student().monthlyPaymentExpiration(),
            UUID.fromString(data.student().conductorId())
    );
    var responsibleData = new CreateStudentWithAddressAndResponsible.ResponsibleData(
            data.responsible().responsibleName(),
            data.responsible().email(),
            data.responsible().phoneNumber()
    );
    var addressData = new CreateStudentWithAddressAndResponsible.AddressData(
            data.address().city(),
            data.address().district(),
            data.address().street(),
            data.address().referencePoint(),
            data.address().houseNumber()
    );
    return new CreateStudentWithAddressAndResponsible.CreateStudentWithAddressAndResponsibleRequest(studentData, responsibleData, addressData);
  }

  public record CreateStudentDto(
          @Valid StudentDataDto student,
          @Valid ResponsibleDataDto responsible,
          @Valid AddressDataDto address
  ) { }

  public record StudentDataDto(
          @NotBlank String studentName,
          @NotBlank String school,
          @NotBlank String grade,
          @NotNull Integer monthlyPayment,
          @NotBlank String monthlyPaymentExpiration,
          @ValidUUID
          String conductorId
  ) { }

  public record ResponsibleDataDto(
          @NotBlank String responsibleName,
          @NotBlank String email,
          @NotBlank String phoneNumber
  ) { }

  public record AddressDataDto(
          @NotBlank String city,
          @NotBlank String district,
          @NotBlank String street,
          @NotBlank String referencePoint,
          @NotNull Integer houseNumber
  ) { }
}
