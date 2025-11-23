package com.greenbeansapps.myschooltransportation.domain.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Student;

public interface CreateMonthlyFeesUseCase {
    public void execute(Student student);
}
