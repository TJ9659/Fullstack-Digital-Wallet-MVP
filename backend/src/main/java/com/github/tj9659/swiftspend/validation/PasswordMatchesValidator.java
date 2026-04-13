package com.github.tj9659.swiftspend.validation;

import com.github.tj9659.swiftspend.dto.RegisterUserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterUserDto> {

    @Override
    public boolean isValid(RegisterUserDto dto, ConstraintValidatorContext context) {
        return dto.getPassword() != null &&
                dto.getPassword().equals(dto.getPasswordConfirmation());
    }
}
