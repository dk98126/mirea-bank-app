package validators;

import validators.annotation.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordConstraint, String> {
    @Override
    public void initialize(PasswordConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length() >= 8 &&
                password.length() <= 64 &&
                password.matches("^[A-Za-z\\d@#'.,:^)(*$%=+!;\"-]{8,64}$") &&
                password.matches("(.*)[@#'.,:^)(*$%=+!;\"-](.*)") &&
                password.matches("(.*)[A-Z](.*)") &&
                password.matches("(.*)[a-z](.*)") &&
                password.matches("(.*)(\\d)(.*)");
    }
}
