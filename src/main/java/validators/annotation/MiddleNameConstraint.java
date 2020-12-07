package validators.annotation;

import validators.MiddleNameValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MiddleNameValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MiddleNameConstraint {
    String message() default "Отчество должно содержать только строчные символы кириллицы и начинаться с прописной буквы";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
