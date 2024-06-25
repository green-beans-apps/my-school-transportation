package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.exceptions.CpfIsNotValidException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidEmailException;
import com.greenbeansapps.myschooltransportation.domain.utils.CapitalizeWords;
import com.greenbeansapps.myschooltransportation.domain.utils.CpfValidator;
import com.greenbeansapps.myschooltransportation.domain.utils.EmailValidator;

import java.util.UUID;

public class Conductor {
    private UUID id;
    private String name;
    private String email;
    private String cpf;
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
}