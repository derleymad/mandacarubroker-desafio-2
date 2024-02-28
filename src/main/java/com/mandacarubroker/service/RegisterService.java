package com.mandacarubroker.service;

import com.mandacarubroker.domain.user.RegisterDTO;
import com.mandacarubroker.domain.user.User;
import com.mandacarubroker.domain.user.UserRepository;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Service
public class RegisterService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    public void validateRequestRegisterDTO(RegisterDTO data) throws Exception {

        Validator validator;

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        Set<ConstraintViolation<RegisterDTO>> violations = validator.validate(data);

        if (!violations.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder("Validation failed. Details: ");

            for (ConstraintViolation<RegisterDTO> violation : violations) {
                errorMessage.append(String.format("[%s: %s], ", violation.getPropertyPath(), violation.getMessage()));
            }

            errorMessage.delete(errorMessage.length() - 2, errorMessage.length());

            throw new ConstraintViolationException(errorMessage.toString(), violations);
        }else {
            if(this.isValidAge(data.birthDate())){
                String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

                User user = new User(data.login(),encryptedPassword, data.role(),data.email(),data.firstName(),data.lastName(),data.birthDate(),data.balance());
                userRepository.save(user);
            } else{
                throw new Exception("Usuário com menos de 18 anos.");
            }
        }
    }

    private LocalDate parsedBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(birthDate, formatter);
    }

    private boolean isValidAge(String birthDate) {
        LocalDate today = LocalDate.now();
        LocalDate parsedDate = parsedBirthDate(birthDate);
        int age = today.getYear() - parsedDate.getYear();
        if (today.getMonthValue() < parsedDate.getMonthValue() || (today.getMonthValue() == parsedDate.getMonthValue() && today.getDayOfMonth() < parsedDate.getDayOfMonth())) {
            age--;
        }
        return age >= 18;
    }

}
