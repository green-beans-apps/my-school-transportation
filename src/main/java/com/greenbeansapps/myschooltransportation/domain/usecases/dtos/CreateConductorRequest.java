package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record CreateConductorRequest(UUID id, String name, String email, String password, String cpf) {
}
