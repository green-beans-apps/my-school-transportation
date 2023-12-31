package com.greenbeansapps.myschooltransportation.domain.services;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

public interface CreateStudentWithAddressAndResponsible {
    public Student execute(Request request);

  public record Request(
          StudentData student,
          ResponsibleData responsible,
          AddressData address
  ) { }

  public record StudentData(String studentName, String school, String grade, Integer monthlyPayment, String monthlyPaymentExpiration, String conductorId) { }

  public record ResponsibleData(String responsibleName, String email, String phoneNumber) { }

  public record AddressData(String city, String district, String street, Integer houseNumber) { }

}
