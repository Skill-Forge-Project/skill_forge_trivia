package bg.trivia.validations.validId;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom validation annotation used to validate the ID of a Question entity.
 * This annotation checks whether a provided ID exists in any of the MongoDB collections
 * and ensures it is valid before proceeding.
 *
 * Usage:
 * Apply this annotation to any field that needs to validate whether the ID exists
 * in the MongoDB collections.
 *
 * Example:
 * {@code @ValidID(message = "Invalid question ID")}
 *
 * @see bg.trivia.validations.validId.ValidIDValidator
 */

@Target(value = ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidIDValidator.class)
public @interface ValidID {

    String message() default "ID is not valid.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
