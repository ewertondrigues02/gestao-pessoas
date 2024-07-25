# Gestão de Pessoas

A aplicação **"Gestão de Pessoas"** é um sistema CRUD para gerenciar informações de pessoas, incluindo criação, leitura, atualização e exclusão de registros. A aplicação utiliza Spring Boot no backend e Vue.js 3 no frontend, com um banco de dados MySQL.

## Sumário
- [Pré-requisitos](#pré-requisitos)
- [Configuração do Backend](#configuração-do-backend)
- [Configuração do Frontend](#configuração-do-frontend)
- [Rodando o Projeto](#rodando-o-projeto)
- [Endpoints da API](#endpoints-da-api)
- [Contribuindo](#contribuindo)
- [Licença](#licença)
- [Links Úteis](#links-úteis)

## Pré-requisitos

Antes de começar, você precisará de:
- **Java**: JDK 22 ou superior
- **Spring Boot**: 3.3.2
- **Node.js**: 16.x ou superior
- **MySQL**: 8.x
- **Docker (opcional)**: Para executar MySQL em um container Docker

## Configuração do Backend

### Banco de Dados

Crie o banco de dados no MySQL:

```sql
CREATE DATABASE gestao_pessoas;

## Crie a tabela tb_pessoa:

```sql
CREATE TABLE tb_pessoa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome varchar(50) NOT NULL,
    cpf char(11) NOT NULL UNIQUE,
    email varchar(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

## Configure o arquivo `application.yml` do Spring Boot:

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

## Rodando o Backend

Instale as dependências:

```bash
./mvnw install

## Iniciar a aplicação Spring Boot

Para iniciar a aplicação Spring Boot, execute o seguinte comando:

```bash
./mvnw spring-boot:run


Configuração do Frontend
Instalação do Node.js e Vue.js
Instale o Node.js: Siga as instruções em Node.js.

Instale o Vue CLI:

```bash
npm install -g @vue/cli

### Configuração e Execução do Frontend

Clone o repositório do frontend:

```bash
git clone https://github.com/seuusuario/seurepositorio-frontend.git

Instale as dependências:

````bash
Copiar código
cd seurepositorio-frontend
npm install

Inicie o servidor de desenvolvimento:

```bash
Copiar código
npm run serve



