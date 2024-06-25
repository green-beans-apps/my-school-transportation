package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record UpdateConductorRequest(UUID conductorId, String name, String email) {
}
