package com.kjt.demo.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.internal.constraintvalidators.hv.ISBNValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ISBNValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ISBN {

    String message() default "validation.book.isbn.invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
