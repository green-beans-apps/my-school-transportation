package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidEmailException;
import com.greenbeansapps.myschooltransportation.domain.utils.EmailValidator;

import java.util.UUID;

public class Responsible {
    private UUID id;
    private String name;
    private String email;
    private String phoneNumber;

    public Responsible() {
    }

    public Responsible(UUID id, String name, String email, String phoneNumber) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhoneNumber(phoneNumber);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        if(id == null) {
            this.id = UUID.randomUUID();
        } else {
            this.id = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }
        if (name.length() < 3) {
            throw new IllegalArgumentException("the name must have more than 3 characters");
        }
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be null or empty");
        }
        if (!EmailValidator.execute(email)) {
            throw new InvalidEmailException();
        }
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        String cleanedPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");

        if (cleanedPhoneNumber.length() < 10 || cleanedPhoneNumber.length() > 15) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        this.phoneNumber = phoneNumber;
    }
}
