package com.mandacarubroker.service;

import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.infra.security.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    //public Optional<User> getUserByUsername(String username) {
      //  return userRepository.findbyLogin(username);
   // }

    public void validateAndDeposit(String username, double amount) throws Exception {

        User user = (User) userRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        } else {
            if (Objects.equals(user.getUsername(), username)) {
                user.deposit(amount);
                userRepository.save(user);

            } else {
                throw new UsernameNotFoundException("Usuário não encontrado.");
            }
        }
    }

    public void validateAndWithdraw(String username, double amount) throws Exception {
        User user = (User) userRepository.findByLogin(username);

        if (user == null) {
            throw new Exception("Usuário não encontrado.");
        } else {
            if (Objects.equals(user.getUsername(), username)) {

                user.withdraw(amount);
                userRepository.save(user);

            } else {
                throw new Exception("Usuário não encontrado.");
            }
        }
    }








}
