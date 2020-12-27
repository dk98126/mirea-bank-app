package validators;

import validators.annotation.LastNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LastNameValidator implements ConstraintValidator<LastNameConstraint, String> {
    @Override
    public void initialize(LastNameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String lastName, ConstraintValidatorContext context) {
        return lastName.matches("^[А-Я][а-я]*$");
    }
}
