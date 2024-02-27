package com.mandacarubroker.domain.user;

import jakarta.validation.constraints.Pattern;

public record RegisterDTO(
        String login,
        String password,
        UserRole role,
        String firstName,
        String lastName,

        @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Birth date must be in the format dd/MM/yyyy")        String birthDate,
        Double balance
        ) { }
