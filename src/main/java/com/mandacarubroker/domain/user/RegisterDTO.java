package com.mandacarubroker.domain.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
        @NotBlank(message = "Login cannot be blank")
        String login,
        @NotBlank(message = "Password cannot be blank")
        String password,
        UserRole role ,
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Firstname cannot be blank")
        String firstName,
        @NotBlank(message = "Lastname cannot be blank")
        String lastName,
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Birth date must be in the format dd/MM/yyyy")        String birthDate,
        Double balance
) {
    public RegisterDTO {
        role = UserRole.USER;
        balance = 0.0;
    }
}
