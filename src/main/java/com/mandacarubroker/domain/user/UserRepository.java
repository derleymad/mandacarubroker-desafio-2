package com.mandacarubroker.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);
    User save(User user);


    //findByLogin2(String login);
    // findByUsername(String login);
}
