    package com.greenbeansapps.myschooltransportation.infra.repositories;

    import com.greenbeansapps.myschooltransportation.domain.entities.Address;
    import com.greenbeansapps.myschooltransportation.infra.repositories.impl.AddressRepositoryJPA;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.boot.test.context.SpringBootTest;
    import org.springframework.test.context.ActiveProfiles;

    import java.util.UUID;

    @SpringBootTest
    @ActiveProfiles("test")
    @DisplayName("Teste de criação de repositório")
    public class AddressRepositoryJPATest {
        @Autowired
        private AddressRepositoryJPA addressRepo;

        @Test
        @DisplayName("Criando endereço")
        public void createAddress() {
            Address address1 = new Address(UUID.randomUUID(), "Recife", "Bairro Tal", "Rua 3", "Próximo ao mercado X", 46);

        }
    }
