package com.greenbeansapps.myschooltransportation.domain.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;
import com.greenbeansapps.myschooltransportation.domain.enums.TransportationType;

import java.util.UUID;

public interface CreateStudentWithAddressAndResponsible {
    public Student execute(CreateStudentWithAddressAndResponsibleRequest request);

  public record CreateStudentWithAddressAndResponsibleRequest(
          StudentData student,
          ResponsibleData responsible,
          AddressData address
  ) { }

  public record StudentData(UUID id, String studentName, String school, String grade, String transportationType, Double monthlyPayment, Integer monthlyPaymentExpiration,  String shift, UUID conductorId) { }

  public record ResponsibleData(UUID id, String responsibleName, String email, String phone) { }

  public record AddressData(UUID id, String city, String district, String street, String referencePoint, String houseNumber) { }

}
