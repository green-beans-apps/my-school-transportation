package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record GetConductorByIdResponse(UUID id, String name, String email, String cpf) {
}
