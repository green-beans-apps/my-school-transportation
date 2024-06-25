package com.greenbeansapps.myschooltransportation.infra.repositories.mappers;

import com.greenbeansapps.myschooltransportation.domain.entities.Address;
import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.entities.Responsible;
import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.AddressSchema;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ConductorSchema;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.ResponsibleSchema;
import com.greenbeansapps.myschooltransportation.infra.repositories.schemas.StudentSchema;
import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class StudentMapper {

  public static Student mapJpaToDomain(StudentSchema studentSchema) {
    var newConductor = new Conductor();
    var newResponsible = new Responsible();
    var newAddress = new Address();

    BeanUtils.copyProperties(studentSchema.getConductor(), newConductor);
    BeanUtils.copyProperties(studentSchema.getResponsible(), newResponsible);
    BeanUtils.copyProperties(studentSchema.getAddress(), newAddress);

    return new Student(studentSchema.getId(), studentSchema.getName(), studentSchema.getSchool(),
            studentSchema.getGrade(), studentSchema.getTransportationType().toString(), studentSchema.getMonthlyPayment(), studentSchema.getMonthlyPaymentExpiration(), studentSchema.getShift().toString(),
            newConductor, newResponsible, newAddress);
  }

  public static StudentSchema mapDomainToJpa(Student student) {
    var newStudent = new StudentSchema();
    BeanUtils.copyProperties(student, newStudent);

    var newConductor = new ConductorSchema();
    BeanUtils.copyProperties(student.getConductor(), newConductor);
    newStudent.setConductor(newConductor);

    var newResponsible = new ResponsibleSchema();
    BeanUtils.copyProperties(student.getResponsible(), newResponsible);
    newStudent.setResponsible(newResponsible);

    var newAddress = new AddressSchema();
    BeanUtils.copyProperties(student.getAddress(), newAddress);
    newStudent.setAddress(newAddress);
    return newStudent;
  }
}
