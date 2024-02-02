package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
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
    public ResponseEntity<StudentProjectionDto> getStudentById(@PathVariable("studentId") UUID studentId) {
        var getStudent = this.getStudentByIdUseCase.execute(studentId);
        return ResponseEntity.status(HttpStatus.OK).body(getStudent);
    }
}
