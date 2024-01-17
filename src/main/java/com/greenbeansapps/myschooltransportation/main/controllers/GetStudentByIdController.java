package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.exeptions.StudentNotFoundException;
import com.greenbeansapps.myschooltransportation.implementation.usecases.GetStudentByIdUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class GetStudentByIdController {

    @Autowired
    private GetStudentByIdUseCaseImpl getStudentByIdUseCase;

    @GetMapping("/{studentId}")
    public ResponseEntity getStudentById(@PathVariable("studentId") UUID studentId) {
        try {
            var newStudent = this.getStudentByIdUseCase.execute(studentId);
            var studentResponseDto = new StudentResponseDto(newStudent.getId(), newStudent.getName(), newStudent.getSchool(), newStudent.getGrade(),
                    newStudent.getMonthlyPayment(), newStudent.getMonthlyPaymentExpiration(), newStudent.getConductor(), newStudent.getResponsible(),
                    newStudent.getAddress());
            return ResponseEntity.status(HttpStatus.OK).body(studentResponseDto);
        } catch (StudentNotFoundException studentNotFoundException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(studentNotFoundException.getMessage());
        } catch (Error err) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err.getMessage());
        }
    }

    public record StudentResponseDto(UUID id, String name, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, Conductor conductor, Responsible responsible, Address address) { }
}