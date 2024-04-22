package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record CreateResponsibleRequest(UUID id, String name, String email, String phone) {
}
