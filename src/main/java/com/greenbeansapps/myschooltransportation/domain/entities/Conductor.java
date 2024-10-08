package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.exceptions.CpfIsNotValidException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidEmailException;
import com.greenbeansapps.myschooltransportation.domain.utils.CapitalizeWords;
import com.greenbeansapps.myschooltransportation.domain.utils.CpfValidator;
import com.greenbeansapps.myschooltransportation.domain.utils.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "conductor")
public class Conductor implements Serializable, UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false, unique = true)
    private String cpf;
    @Column(nullable = false)
    private String password;

    public Conductor() {
    }

    public Conductor(UUID id, String name, String email, String cpf, String password) {
        setId(id);
        setName(name);
        setEmail(email);
        setCpf(cpf);
        setPassword(password);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if (id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
    }
    public void setId() {
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("the name must have more than 3 characters");
        }
        this.name = CapitalizeWords.execute(name);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        if (!EmailValidator.execute(email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null || cpf.isEmpty()) {
            throw new IllegalArgumentException("Cpf cannot be null or empty");
        }
        String cleanedCpf = cpf.replaceAll("[^0-9]", "");

        if (cleanedCpf.length() != 11) {
            throw new CpfIsNotValidException();
        }
        if (!CpfValidator.execute(cleanedCpf)) {
            throw new CpfIsNotValidException();
        }
        this.cpf = cleanedCpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.cpf;
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