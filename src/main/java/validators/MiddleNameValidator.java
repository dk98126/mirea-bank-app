package validators;

import validators.annotation.MiddleNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MiddleNameValidator implements ConstraintValidator<MiddleNameConstraint, String> {
    @Override
    public void initialize(MiddleNameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String middleName, ConstraintValidatorContext context) {
        return middleName.matches("^([А-Я][а-я]*)?$");
    }
}
