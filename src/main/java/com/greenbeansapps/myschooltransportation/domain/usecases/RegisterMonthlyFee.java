package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.usecases.dtos.RegisterMonthlyFeeRequest;

public interface RegisterMonthlyFee {
    public void execute(RegisterMonthlyFeeRequest data);
}
