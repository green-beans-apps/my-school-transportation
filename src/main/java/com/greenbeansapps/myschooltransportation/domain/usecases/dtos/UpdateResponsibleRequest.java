package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record UpdateResponsibleRequest(UUID studentId, String name, String email, String phone) {
}
