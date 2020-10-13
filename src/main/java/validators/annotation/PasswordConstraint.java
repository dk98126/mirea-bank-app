package validators.annotation;

import validators.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordConstraint {
    String message() default "Пароль должен быть длиной не менее 8 символов и содержать прописную и строчную буквы латинского алфавита, цифру и спецсимвол";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}