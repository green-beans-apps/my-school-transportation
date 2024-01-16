package com.greenbeansapps.myschooltransportation.main.constraints;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUUID {
  String message() default "O ID do condutor deve ser um UUID válido";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}