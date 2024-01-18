package com.greenbeansapps.myschooltransportation.main.controllers;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.implementation.usecases.GetAllStudentsByConductorIdUseCaseImpl;
import com.greenbeansapps.myschooltransportation.main.constraints.UUIDValidator;
import com.greenbeansapps.myschooltransportation.main.constraints.ValidUUID;
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
@RequestMapping("conductor")
public class GetAllStudentsByConductorIdController {


    @Autowired
    GetAllStudentsByConductorIdUseCaseImpl getAllStudentsByConductorIdUseCase;

    @Autowired
    UUIDValidator uuidValidator;

    @GetMapping("/{conductorId}/student")
    public ResponseEntity<List<Student>> getAllStudentsByConductorId(@PathVariable("conductorId") String conductorId)  {
        var students = getAllStudentsByConductorIdUseCase.execute(UUID.fromString(conductorId));
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }

}
