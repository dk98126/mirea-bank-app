package validators;

import validators.annotation.PhoneNumberConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {
    @Override
    public void initialize(PhoneNumberConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        return phoneNumber.matches("^(\\+7|7|8)?[( \\-]?\\d{3}[) \\-]?\\d{3}[- ]?\\d{2}[- ]?\\d{2}$");
    }
}
