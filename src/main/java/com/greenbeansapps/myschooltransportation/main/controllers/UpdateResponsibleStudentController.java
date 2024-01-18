package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.implementation.usecases.UpdateResponsibleStudentUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.controllers.erros.ErrorResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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
public class UpdateResponsibleStudentController {

    @Autowired
    private UpdateResponsibleStudentUseCaseImpl updateResponsibleStudentUseCase;

    @PutMapping("/responsible")
    public ResponseEntity updateResponsibleStudent(@RequestBody @Valid UpdateResponsibleStudentDto updateResponsibleStudentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ErrorResponse errorResponse = new ErrorResponse();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        var updateResponsible = this.updateResponsibleStudentUseCase.execute(updateResponsibleStudentDto.studentId, updateResponsibleStudentDto.name,
                updateResponsibleStudentDto.email, updateResponsibleStudentDto.phoneNumber);
        var newResponsible = new UpdateResponsibleStudentResponsiveDto(updateResponsible.getId(), updateResponsible.getName(), updateResponsible.getEmail(),
                updateResponsible.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(newResponsible);
    }

    public record UpdateResponsibleStudentDto(@NotNull UUID studentId, @NotBlank String name, @Email String email, @NotBlank String phoneNumber) { }
    public record UpdateResponsibleStudentResponsiveDto(UUID responsibleId, String name, String email, String phoneNumber) { }
}
