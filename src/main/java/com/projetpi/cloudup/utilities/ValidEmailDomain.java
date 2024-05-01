package com.projetpi.cloudup.utilities;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;
@Documented
@Constraint(validatedBy = EmailDomainValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailDomain {
    String message() default "Email doit appartenir au domaine spécifié @esprit.tn";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String domain() default "esprit.tn";
}
