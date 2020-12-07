package validators.annotation;

import validators.LastNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LastNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LastNameConstraint {
    String message() default "Фамилия должна содержать только строчные символы кириллицы и начинаться с прописной буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
