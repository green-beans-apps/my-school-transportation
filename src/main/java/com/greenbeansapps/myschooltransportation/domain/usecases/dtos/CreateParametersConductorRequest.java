package com.greenbeansapps.myschooltransportation.domain.usecases.dtos;

import java.util.UUID;

public record CreateParametersConductorRequest (UUID conductorId, Float percentContractTermination) {}
