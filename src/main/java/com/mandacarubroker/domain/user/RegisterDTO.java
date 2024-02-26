package com.mandacarubroker.domain.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
