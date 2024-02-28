package com.mandacarubroker.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@Entity(name = "users")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String login;
    private String password;
    @Getter
    private String email;
    private String firstName;
    private String lastName;
    private String birthDate; // lembrar * apenas para maiores de 18 anos
    private Double balance;

    private UserRole role; // estamos adicionando um extra para administrar quem pode inserir novas ações

    public User(String login, String encryptedPassword, UserRole role, String email,String firstName, String lastName, String birthDate, Double balance){
        this.login = login;
        this.password = encryptedPassword;
        this.role = role;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.balance = balance != null ? balance : 0.0;
    }

    public void deposit(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("O valor do depósito deve ser maior que zero.");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if (amount <= 0) {
            throw new Exception("O valor do saque deve ser maior que zero.");
        }
        if (amount > this.balance) {
            throw new Exception("Saldo insuficiente para realizar o saque.");
        }
        this.balance -= amount;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN) return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        else return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
