# Gestão de Pessoas

**Gestão de Pessoas** é um sistema CRUD para o gerenciamento de informações de pessoas, incluindo operações de criação, leitura, atualização e exclusão de registros. A aplicação é desenvolvida com Spring Boot no backend e Vue.js 3 no frontend, utilizando um banco de dados MySQL.

## Sumário

- [Pré-requisitos](#pré-requisitos)
- [Configuração do Backend](#configuração-do-backend)
  - [Banco de Dados](#banco-de-dados)
- [Configuração do Frontend](#configuração-do-frontend)
  - [Instalação do Node.js e Vue.js](#instalação-do-nodejs-e-vuejs)
- [Rodando o Projeto](#rodando-o-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Contribuindo](#contribuindo)
- [Licença](#licença)
- [Links Úteis](#links-úteis)

## Pré-requisitos

Antes de iniciar, assegure-se de ter os seguintes requisitos instalados:

- **Java:** JDK 22 ou superior
- **Spring Boot:** 3.3.2
- **Node.js:** 16.x ou superior
- **MySQL:** 8.x
- **Docker (opcional):** Para executar MySQL em um container Docker

## Configuração do Backend

### Banco de Dados

1. **Crie o banco de dados no MySQL:**

    ```sql
    CREATE DATABASE gestao_pessoas;
    ```

2. **Crie a tabela `tb_pessoa`:**

    ```sql
    CREATE TABLE tb_pessoa (
        id BIGINT NOT NULL AUTO_INCREMENT,
        nome VARCHAR(50) NOT NULL,
        cpf CHAR(11) NOT NULL UNIQUE,
        email VARCHAR(255) NOT NULL,
        data_nascimento DATE NOT NULL,
        PRIMARY KEY (id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
    ```

3. **Configure o arquivo `application.yml` do Spring Boot:**

    ```yaml
    server:
      port: 8081

    spring:
      application:
        name: gestao-pessoa
      datasource:
        url: jdbc:mysql://localhost:3306/gestao_pessoas?allowPublicKeyRetrieval=true&useSSL=false
        username: ewerton
        password: ew1234
        driver-class-name: com.mysql.cj.jdbc.Driver
      jpa:
        hibernate:
          ddl-auto: update
        show-sql: true
        properties:
          hibernate:
            dialect: org.hibernate.dialect.MySQLDialect

    springdoc:
      api-docs:
        path: /v3/api-docs
      swagger-ui:
        path: /swagger-ui.html
    ```

### Rodando o Backend

1. **Instale as dependências:**

    ```bash
    ./mvnw install
    ```

2. **Inicie a aplicação Spring Boot:**

    ```bash
    ./mvnw spring-boot:run
    ```

## Configuração do Frontend

### Instalação do Node.js e Vue.js

1. **Instale o Node.js:** Siga as instruções em [Node.js](https://nodejs.org/).

2. **Instale o Vue CLI:**

    ```bash
    npm install -g @vue/cli
    ```

### Configuração e Execução do Frontend

1. **Clone o repositório do frontend:**

    ```bash
    git clone https://github.com/seuusuario/seurepositorio-frontend.git
    ```

2. **Instale as dependências:**

    ```bash
    cd seurepositorio-frontend
    npm install
    ```

3. **Inicie o servidor de desenvolvimento:**

    ```bash
    npm run serve
    ```

## Rodando o Projeto

Para executar a aplicação completa, você precisará iniciar tanto o backend quanto o frontend:

1. **Inicie o backend** seguindo as instruções acima.
2. **Inicie o frontend** seguindo as instruções acima.

A aplicação estará disponível nos seguintes endereços:

- **Backend:** [http://localhost:8081](http://localhost:8081)
- **Frontend:** [http://localhost:8080](http://localhost:8080)

## Endpoints da API

A API oferece os seguintes endpoints principais:

- **GET /api/pessoas:** Lista todas as pessoas cadastradas no banco de dados.
- **POST /api/pessoas:** Insere uma nova pessoa e verifica a validade do CPF.
- **GET /api/pessoas/{id}:** Obtém detalhes de uma pessoa pelo ID.
- **PUT /api/pessoas/{id}:** Atualiza os dados de uma pessoa existente e valida o CPF.
- **DELETE /api/pessoas/{id}:** Remove uma pessoa pelo ID.

A documentação da API está disponível em [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html).

## Contribuindo

Para contribuir com o projeto, siga estas etapas:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature:

    ```bash
    git checkout -b minha-nova-feature
    ```

3. Faça suas alterações e commit:

    ```bash
    git commit -am 'Adiciona nova feature'
    ```

4. Faça o push para a branch:

    ```bash
    git push origin minha-nova-feature
    ```

5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a Licença XYZ. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.

## Links Úteis

- [Documentação do Spring Boot](https://spring.io/projects/spring-boot)
- [Documentação do Vue.js](https://vuejs.org/)
- [Documentação do MySQL](https://dev.mysql.com/doc/)
- [Documentação do Flyway](https://flywaydb.org/documentation/)

 # Rodar a Aplicação Frontend com Vue.js 3

Para rodar a aplicação frontend usando Vue.js 3, siga estas etapas:

## 1. Instalar Dependências

Primeiro, certifique-se de que você tem o Node.js e o npm (ou yarn) instalados. No diretório raiz do projeto, execute o seguinte comando para instalar todas as dependências do projeto:

```bash
npm install
```

Isso iniciará a aplicação e você poderá acessá-la em [http://localhost:8080](http://localhost:8080) (ou a porta especificada na configuração).

## Construir a Aplicação para Produção

Quando você estiver pronto para implantar a aplicação, execute o comando para criar a versão otimizada para produção:

```bash
npm run build
```

Os arquivos de construção serão gerados na pasta dist, prontos para serem implantados em um servidor.

Verificar o Código e Aplicar Correções
Para verificar o código e aplicar correções de estilo, execute:

```bash
npm run lint
```
Isso executará o linter para garantir que o código esteja em conformidade com as regras especificadas.

## Configurações Adicionais

### Dependências

- **axios**: Para fazer requisições HTTP.
- **core-js**: Biblioteca para suportar novas funcionalidades JavaScript.
- **vue**: Framework principal.
- **vue-router**: Para gerenciamento de rotas.

### DevDependencies

- Ferramentas de desenvolvimento e configuração como Babel e ESLint.

### Configuração do ESLint

- O ESLint está configurado para verificar o código em conformidade com as regras recomendadas e específicas para Vue 3.

### Compatibilidade de Navegadores

- A configuração `browserslist` garante suporte para navegadores modernos e evita navegadores desatualizados.

### Dependências do Maven

- **spring-boot-starter-actuator**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-actuator

- **springdoc-openapi-starter-webmvc-ui**
  - **Grupo:** org.springdoc
  - **ArtifactId:** springdoc-openapi-starter-webmvc-ui
  - **Versão:** 2.0.4

- **spring-boot-starter-data-jpa**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-data-jpa

- **spring-boot-starter-validation**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-validation

- **spring-boot-starter-web**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-web

- **flyway-core**
  - **Grupo:** org.flywaydb
  - **ArtifactId:** flyway-core

- **flyway-mysql**
  - **Grupo:** org.flywaydb
  - **ArtifactId:** flyway-mysql

- **spring-boot-starter-thymeleaf**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-thymeleaf

- **spring-boot-devtools**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-devtools
  - **Escopo:** runtime
  - **Opcional:** true

- **mysql-connector-j**
  - **Grupo:** com.mysql
  - **ArtifactId:** mysql-connector-j
  - **Escopo:** runtime

- **lombok**
  - **Grupo:** org.projectlombok
  - **ArtifactId:** lombok
  - **Opcional:** true

- **spring-boot-starter-test**
  - **Grupo:** org.springframework.boot
  - **ArtifactId:** spring-boot-starter-test
  - **Escopo:** test







