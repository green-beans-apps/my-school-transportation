package com.greenbeansapps.myschooltransportation.domain.entities;

import com.greenbeansapps.myschooltransportation.domain.exceptions.InvalidEmailException;
import com.greenbeansapps.myschooltransportation.domain.utils.CapitalizeWords;
import com.greenbeansapps.myschooltransportation.domain.utils.EmailValidator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "responsible")
public class Responsible implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column()
    private String email;
    @Column(nullable = false)
    private String phone;

    public Responsible() {
    }

    public Responsible(UUID id, String name, String email, String phone) {
        setId(id);
        setName(name);
        setEmail(email);
        setPhone(phone);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
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
            this.email = email;
        } else if (!EmailValidator.execute(email)) {
            throw new InvalidEmailException();
        } else {
            this.email = email;
        }
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }

        String cleanedPhone = phone.replaceAll("[^0-9]", "");

        if (cleanedPhone.length() < 10 || cleanedPhone.length() > 15) {
            throw new IllegalArgumentException("Invalid phone number");
        }

        this.phone = phone;
    }


}
