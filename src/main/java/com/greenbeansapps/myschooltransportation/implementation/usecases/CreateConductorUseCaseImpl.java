package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.exceptions.CpfAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.EmailAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.domain.exceptions.PasswordIsNotValidException;
import com.greenbeansapps.myschooltransportation.domain.usecases.CreateConductorUseCase;
import com.greenbeansapps.myschooltransportation.implementation.protocols.helpers.CryptoHelper;
import com.greenbeansapps.myschooltransportation.implementation.protocols.repositories.ConductorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CreateConductorUseCaseImpl implements CreateConductorUseCase {
    private final ConductorRepository conductorRepo;
    private final CryptoHelper crypto;

    public CreateConductorUseCaseImpl(ConductorRepository conductorRepo, CryptoHelper crypto) {
        this.conductorRepo = conductorRepo;
        this.crypto = crypto;
    }

    @Override
    public Conductor execute(UUID id, String name, String email, String password, String cpf) {

        Optional<Conductor> cpfIsRegistered = this.conductorRepo.findByCpf(cpf);
        Optional<Conductor> emailIsRegistered = this.conductorRepo.findByEmail(email);
        if(cpfIsRegistered.isPresent()) {
            throw new CpfAlreadyRegisteredException();
        }
        if(emailIsRegistered.isPresent()){
            throw new EmailAlreadyRegisteredException();
        }

        String regexPassword = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[a-zA-Z0-9]).{6,}$";
        Pattern patternRegexPassword = Pattern.compile(regexPassword);
        Matcher matcherPassword = patternRegexPassword.matcher(password);
        if(!matcherPassword.matches()) {
            throw new PasswordIsNotValidException();
        }

        var newConductor = new Conductor(id, name, email, cpf, this.crypto.generateRash(password));
        this.conductorRepo.create(newConductor);
        return newConductor;
    }
}
