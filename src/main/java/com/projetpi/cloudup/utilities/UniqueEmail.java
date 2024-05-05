package com.projetpi.cloudup.utilities;
import com.projetpi.cloudup.repository.UserRepository;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.*;
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =UniqueEmailValidator.class)
@Documented
public @interface UniqueEmail {
    String message() default "Email is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
