# API REST - Todo list

## Descrição

Esta API é uma aplicação RESTful desenvolvida em Java utilizando Spring Boot. Ela oferece endpoints para criação, atualização, exclusão, etc. A documentação da API está disponível via Swagger, facilitando a visualização e interação com os endpoints.

**A minha aplicação foi desenvolvida seguindo boas práticas de desenvolvimento de APIs RESTful. Ela está estruturada em camadas, incluindo:**

- **Controladores (Controllers)**: Responsáveis por receber as requisições HTTP e retornar as respostas apropriadas.
- **Serviços (Service Layer)**: Onde reside a lógica de negócios, separando as regras de negócio da camada de controle.
- **Data Transfer Objects (DTOs)**: Utilizados para transferir dados entre as camadas de forma segura e eficiente, garantindo que apenas os dados necessários sejam expostos.
- **Tratamento Personalizado de Exceções**: A aplicação implementa um tratamento robusto e personalizado de exceções, garantindo que erros sejam gerenciados de forma consistente e que as respostas HTTP retornem mensagens claras e apropriadas.


## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada para o desenvolvimento da API.
- **Spring Boot**: Framework utilizado para simplificar a criação de aplicações Java baseadas em Spring.
- **Swagger**: Utilizado para gerar a documentação interativa da API.
- **PostgreSQL**: PostgreSQL é utilizado para armazenar, gerenciar e consultar dados estruturados e relacionais em aplicações de diversas escalas, oferecendo suporte avançado para transações complexas, índices, consultas e extensões personalizadas.

## Pré-requisitos

- **Java 17** ou superior
- **Maven** para gerenciamento de dependências

## Como Executar a Aplicação

1. Clone o repositório:

   ```bash
   git clone https://github.com/guidias2002/todo-list.git

  
   Navegue até o diretório do projeto:
   cd seu-repositorio

   Compile e execute a aplicação usando Maven:
   mvn clean install
   mvn spring-boot:run


## Endpoints

A aplicação estará disponível em `http://localhost:8080`.

- `POST  /task`: Cria uma tarefa

- `PUT  /task/{id}`: Atualiza a tarefa

- `PUT  /task/{id}/complete`: Conclui a tarefa

- `PUT  /task/{id}/reopen-task`: Reabre a tarefa

- `GET  /task`: Retorna uma lista de tarefas

- `GET  /task/complete`: Retorna uma lista das tarefas concluídas

- `GET  /task/incomplete`: Retorna uma lista das tarefas não concluídas

- `GET   /task/{id}`: Retorna a tarefa pelo Id

- `DELETE  /task`: Deleta a tarefa pelo Id

## A documentação da API pode ser acessada através do Swagger UI. Após iniciar a aplicação, a documentação estará disponível em:

- `http://localhost:8080/swagger-ui.html`

