package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record UpdateAddressRequest(UUID studentId, String city, String district, String street, String referencePoint, String houseNumber) {
}
