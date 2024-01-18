package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.implementation.usecases.UpdateResponsibleStudentUseCaseImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity updateResponsibleStudent(@RequestBody @Valid UpdateResponsibleStudentDto updateResponsibleStudentDto) {
        var updateResponsible = this.updateResponsibleStudentUseCase.execute(updateResponsibleStudentDto.studentId, updateResponsibleStudentDto.name,
                updateResponsibleStudentDto.email, updateResponsibleStudentDto.phoneNumber);
        var newResponsible = new UpdateResponsibleStudentResponsiveDto(updateResponsible.getId(), updateResponsible.getName(), updateResponsible.getEmail(),
                updateResponsible.getPhoneNumber());
        return ResponseEntity.status(HttpStatus.OK).body(newResponsible);
    }

    public record UpdateResponsibleStudentDto(@NotNull UUID studentId, @NotBlank String name, @NotBlank String email, @NotBlank String phoneNumber) { }
    public record UpdateResponsibleStudentResponsiveDto(UUID responsibleId, String name, String email, String phoneNumber) { }
}
