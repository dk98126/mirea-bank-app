package com.dk98126.mireabankapp.model.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class UpdateFullNameForm {

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "Имя должно содержать только символы кириллицы и начинаться с прописной буквы")
    String firstName;

    @NotBlank
    @Pattern(regexp = "^[А-Я][а-я]*$", message = "Фамилия должна содержать только символы кириллицы и начинаться с прописной буквы")
    String lastName;

    @Pattern(regexp = "^([А-Я][а-я]*)?$", message = "Отчество должно содержать только символы кириллицы и начинаться с прописной буквы")
    String middleName;

}
