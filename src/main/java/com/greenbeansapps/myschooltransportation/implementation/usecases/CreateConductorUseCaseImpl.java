package com.greenbeansapps.myschooltransportation.implementation.usecases;

import com.greenbeansapps.myschooltransportation.domain.entities.Conductor;
import com.greenbeansapps.myschooltransportation.domain.exeptions.CpfAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.EmailAlreadyRegisteredException;
import com.greenbeansapps.myschooltransportation.domain.exeptions.PasswordIsNotValidException;
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
    public Conductor execute(String name, String email, String password, String cpf) {

        String regexPassword = "^(?=.*[!@#$%^&*(),.?\":{}|<>])(?=.*[a-zA-Z0-9]).{6,}$";
        // Compila a expressão
        Pattern patternRegexPassword = Pattern.compile(regexPassword);
        // Cria um objeto Matcher
        Matcher matcherPassword = patternRegexPassword.matcher(password);
        if(!matcherPassword.matches()) {
            throw new PasswordIsNotValidException();
        }

        Optional<Conductor> cpfIsRegistered = this.conductorRepo.findByCpf(cpf);
        Optional<Conductor> emailIsRegistered = this.conductorRepo.findByEmail(email);
        if(cpfIsRegistered.isPresent()) {
            throw new CpfAlreadyRegisteredException();
        }
        if(emailIsRegistered.isPresent()){
            throw new EmailAlreadyRegisteredException();
        }

        var newConductor = new Conductor(UUID.randomUUID(), name, email, cpf, this.crypto.generateRash(password));
        this.conductorRepo.create(newConductor);
        return newConductor;
    }
}
