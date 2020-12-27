package validators.annotation;

import validators.FirstNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FirstNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FirstNameConstraint {
    String message() default "Имя должно содержать только строчные символы кириллицы и начинаться с прописной буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
