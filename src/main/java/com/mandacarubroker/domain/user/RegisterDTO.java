package com.mandacarubroker.domain.user;

import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
        String login,
        String password,
        @Null(message = "Role must be null")
        UserRole role ,
        String firstName,
        String lastName,
        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Birth date must be in the format dd/MM/yyyy")        String birthDate,
        Double balance
) {
    public RegisterDTO {
        role = UserRole.USER;
    }
}
