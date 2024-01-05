    package com.greenbeansapps.myschooltransportation.infra.repositories;

    import com.greenbeansapps.myschooltransportation.domain.entities.Address;
    import com.greenbeansapps.myschooltransportation.infra.repositories.impl.AddressRepositoryJPA;
    import org.junit.jupiter.api.Assertions;
    import org.junit.jupiter.api.DisplayName;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
    import org.springframework.test.context.ActiveProfiles;

    import java.util.UUID;

    @DataJpaTest
    @ActiveProfiles("test")
    @DisplayName("Teste de criação de repositório")
    public class AddressRepositoryJPATest {
        @Autowired
        private AddressRepositoryJPA adressRepo;

        @Test
        @DisplayName("Criando endereço")
        public void createAddress() {
            Address address1 = new Address(UUID.randomUUID(), "Recife", "Bairro Tal", "Rua 3", 46);
            adressRepo.create(address1);

            Assertions.assertEquals(address1.getId(), adressRepo.findById(address1.getId()));
        }
    }
