package com.dk98126.mireabankapp.model.form;

import lombok.Data;
import validators.annotation.FirstNameConstraint;
import validators.annotation.LastNameConstraint;
import validators.annotation.MiddleNameConstraint;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateFullNameForm {

    @NotBlank
    @FirstNameConstraint
    String firstName;

    @NotBlank
    @LastNameConstraint
    String lastName;

    @MiddleNameConstraint
    String middleName;

}
