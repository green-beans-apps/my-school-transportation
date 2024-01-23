# Minha Condução Escolar

O aplicativo **Minha Condução Escolar** surge como uma solução abrangente para simplificar a gestão de negócios de motoristas autônomos de transporte escolar. O principal objetivo é proporcionar um controle aprimorado, eliminando a dependência de métodos tradicionais, como planilhas e papel, ao oferecer uma plataforma intuitiva e eficaz que pode ser acessada diretamente pelo celular.

## Setup da aplicação (local)

Antes de rodar a aplicação é preciso garantir que as seguintes dependências estejam corretamente instaladas:

```
Java 17
PostgreSQL 16.0
Maven 3.4.0 
Flyway Migration
Lombok
```

## Preparando ambiente

É necessário a criação da base de dados relacional no Postgres

```
CREATE DATABASE "my-school-transportation";
```

## Instalação da aplicação

Primeiramente, faça o clone do repositório:
```
https://github.com/green-beans-apps/my-school-transportation.git
```

Feito isso, abra o projeto na sua IDE:

Agora iremos iniciar a aplicação:
```
Acesse a classe main e aperte run.
```
Pronto. A aplicação está disponível em http://localhost:8080
```
Tomcat started on port(s): 8080 (http)
```

## Tecnologias e Metodologias Utilizadas
- Java: A escolha da linguagem Java para o desenvolvimento do projeto proporciona uma plataforma robusta e versátil.

- Spring boot: O Spring Boot é adotado como framework principal devido à sua capacidade de simplificar o desenvolvimento de aplicativos Java. Ele oferece uma abordagem "convenção sobre configuração", facilitando a configuração e o desenvolvimento ágil. 

- Arquitetura Limpa: A adoção da arquitetura limpa (Clean Arch) é fundamental para manter a modularidade, escalabiliddade e testabilidade do sistema. Essa abordagem arquitetônica promove a separação de preocupações, organizando o código em camadas independentes. Dessa forma, a lógica de negócios fica isolada das implementações técnicas, tornando o sistema mais flexível e adaptável a mudanças.

- Scrum: A escolha do Scrum como metodologia de desenvolvimento destaca-se pela ênfase na entrega incremental e na colaboração entre os membros da equipe. Os ciclos curtos de desenvolvimento, conhecidos como sprints, permitem uma rápida adaptação às mudanças nos requisitos do projeto.

## Contribuição
Contribuições são bem-vindas! Sinta-se à vontade para abrir problemas (issues) e enviar pull requests.
1. Faça o fork do projeto (Fork no canto superior direito da página).
2. Crie uma branch para sua funcionalidade (`git checkout -b feature/nova-feature`).
3. Faça commit de suas alterações (`git commit -m 'Adiciona nova feature'`).
4. Faça push para a branch (`git push origin feature/nova-feature`).
5. Abra um Pull Request.
