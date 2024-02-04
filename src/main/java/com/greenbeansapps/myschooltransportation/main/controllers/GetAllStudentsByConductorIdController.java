package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionDto;
import com.greenbeansapps.myschooltransportation.domain.dto.StudentProjectionWithPaymentProjectionDto;
import com.greenbeansapps.myschooltransportation.implementation.usecases.GetAllStudentsByConductorIdUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/student")
public class GetAllStudentsByConductorIdController {

    @Autowired
    GetAllStudentsByConductorIdUseCaseImpl getAllStudentsByConductorIdUseCase;

    @GetMapping("conductor/{conductorId}")
    public ResponseEntity<List<StudentProjectionWithPaymentProjectionDto>> getAllStudentsByConductorId(@PathVariable("conductorId") String conductorId)  {
        var students = getAllStudentsByConductorIdUseCase.execute(UUID.fromString(conductorId));
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

}
