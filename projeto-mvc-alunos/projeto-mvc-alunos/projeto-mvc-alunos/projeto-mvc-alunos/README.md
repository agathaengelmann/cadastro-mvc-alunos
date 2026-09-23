# Projeto MVC - Cadastro de Alunos

Projeto didático para demonstrar MVC com Java, Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA e H2.

## Requisitos

- Java 17 ou superior
- Maven 3.6.3 ou superior, ou uma IDE com suporte Maven

## Executar

No terminal, dentro da pasta do projeto:

```bash
mvn spring-boot:run
```

Depois acesse:

- Aplicação: http://localhost:8080/alunos
- Console H2: http://localhost:8080/h2-console

No H2 Console use:

- JDBC URL: `jdbc:h2:mem:escola`
- User Name: `sa`
- Password: deixe em branco

## Onde está o MVC?

- **Model:** `model/Aluno.java`
- **Controller:** `controller/AlunoController.java`
- **View:** `resources/templates/alunos/*.html`

Camadas auxiliares:

- **Service:** regras de negócio
- **Repository:** acesso aos dados

## Fluxo principal

Navegador -> Controller -> Service -> Repository -> H2

H2 -> Repository -> Service -> Controller -> Thymeleaf -> HTML -> Navegador
