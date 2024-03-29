package com.mandacarubroker.controller;

import com.mandacarubroker.domain.user.UserRepository;
import com.mandacarubroker.infra.security.TokenService;
import com.mandacarubroker.service.UserService;
import com.mandacarubroker.utils.ErrorResponse;
import com.mandacarubroker.utils.OkResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestHeader("Authorization") String authorizationHeader,@RequestParam double amount) {
        String jwtToken = authorizationHeader.substring(7); // ignora o prefixo "Bearer "
        String username = tokenService.getUsernameFromToken(jwtToken);

        try{
            userService.validateAndDeposit(username, amount);
            return ResponseEntity.ok().body(new OkResponse("Depósito realizado com sucesso."));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestHeader("Authorization") String authorizationHeader, @RequestParam double amount) {
        String jwtToken = authorizationHeader.substring(7); // ignora o prefixo "Bearer "
        String username = tokenService.getUsernameFromToken(jwtToken);

        try {
            userService.validateAndWithdraw(username, amount);
            return ResponseEntity.ok().body(new OkResponse("Saque realizado com sucesso."));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }

    }
}
