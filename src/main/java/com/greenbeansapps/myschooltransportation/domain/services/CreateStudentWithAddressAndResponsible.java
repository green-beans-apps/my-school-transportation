package com.greenbeansapps.myschooltransportation.domain.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

import java.util.UUID;

public interface CreateStudentWithAddressAndResponsible {
    public Student execute(CreateStudentWithAddressAndResponsibleRequest request);

  public record CreateStudentWithAddressAndResponsibleRequest(
          StudentData student,
          ResponsibleData responsible,
          AddressData address
  ) { }

  public record StudentData(String studentName, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, UUID conductorId) { }

  public record ResponsibleData(String responsibleName, String email, String phoneNumber) { }

  public record AddressData(String city, String district, String street, String referencePoint, Integer houseNumber) { }

}
