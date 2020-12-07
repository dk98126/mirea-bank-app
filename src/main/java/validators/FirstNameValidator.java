package validators;

import validators.annotation.FirstNameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FirstNameValidator implements ConstraintValidator<FirstNameConstraint, String> {
    @Override
    public void initialize(FirstNameConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String firstName, ConstraintValidatorContext context) {
        return firstName.matches("^[А-Я][а-я]*$");
    }
}
