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

  public record StudentData(String studentName, String school, String grade, String transportationType, Integer monthlyPayment, String monthlyPaymentExpiration, UUID conductorId) { }

  public record ResponsibleData(String responsibleName, String email, String phoneNumber) { }

  public record AddressData(String city, String district, String street, String referencePoint, Integer houseNumber) { }

}
