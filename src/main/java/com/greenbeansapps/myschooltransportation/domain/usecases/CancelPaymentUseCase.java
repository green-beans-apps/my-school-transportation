package com.greenbeansapps.myschooltransportation.domain.usecases;

import java.util.UUID;

public interface CancelPaymentUseCase {
    public Boolean execute(UUID paymentId);
}
