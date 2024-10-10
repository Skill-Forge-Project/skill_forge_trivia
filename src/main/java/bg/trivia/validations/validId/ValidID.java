package bg.trivia.validations.validId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIDValidator.class)
public @interface ValidID {

    String message() default "ID is not valid.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
