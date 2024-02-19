package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.implementation.usecases.UpdateAddressStudentUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class UpdateAddressStudentController {

    @Autowired
    private UpdateAddressStudentUseCaseImpl updateAddressStudentUseCase;

    @PutMapping("/address")
    public ResponseEntity updateAddressStudent(@RequestBody @Valid UpdateAddressStudentDto updateAddressStudentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        var updateAddress = this.updateAddressStudentUseCase.execute(updateAddressStudentDto.studentId, updateAddressStudentDto.city, updateAddressStudentDto.district,
                updateAddressStudentDto.street, updateAddressStudentDto.referencePoint(), updateAddressStudentDto.houseNumber());

        var newAddress = new UpdateAddressStudentResponseDto(updateAddress.getId(), updateAddress.getCity(), updateAddress.getDistrict(), updateAddress.getStreet(),
                updateAddress.getReferencePoint(), updateAddress.getHouseNumber());

        return ResponseEntity.status(HttpStatus.OK).body(newAddress);
    }

    public record UpdateAddressStudentDto(@NotNull UUID studentId, @NotBlank String city, @NotBlank String district, @NotBlank String street, @NotBlank String referencePoint, String houseNumber) { }
    public record UpdateAddressStudentResponseDto(UUID addressId, String city, String district, String street, String referencePoint, String houseNumber) { }
}
