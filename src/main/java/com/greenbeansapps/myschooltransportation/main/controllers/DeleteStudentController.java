package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.implementation.usecases.DeleteStudentUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/student")
public class DeleteStudentController {

    @Autowired
    private DeleteStudentUseCaseImpl deleteStudentUseCase;

    @DeleteMapping("/{studentId}")
    public ResponseEntity deleteStudent(@PathVariable("studentId") UUID studentId) {
        deleteStudentUseCase.execute(studentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
