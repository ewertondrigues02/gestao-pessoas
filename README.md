Gestão de Pessoas
A aplicação "Gestão de Pessoas" é um sistema CRUD para gerenciar informações de pessoas, incluindo criação, leitura, atualização e exclusão de registros. A aplicação utiliza Spring Boot no backend e Vue.js 3 no frontend, com um banco de dados MySQL.

Sumário
Pré-requisitos
Configuração do Backend
Configuração do Frontend
Rodando o Projeto
Endpoints da API
Contribuindo
Licença
Links Úteis
Pré-requisitos
Antes de começar, você precisará de:

Java: JDK 22 ou superior
Spring Boot: 3.3.2
Node.js: 16.x ou superior
MySQL: 8.x
Docker (opcional): Para executar MySQL em um container Docker
Configuração do Backend
Banco de Dados
Crie o banco de dados no MySQL:
sql
Copiar código
CREATE DATABASE gestao_pessoas;
Crie a tabela tb_pessoa:
sql
Copiar código
CREATE TABLE tb_pessoa (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome varchar(50) NOT NULL,
    cpf char(11) NOT NULL UNIQUE,
    email varchar(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
Configure o arquivo application.yml do Spring Boot:
yaml
Copiar código
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
Rodando o Backend
Instale as dependências:
bash
Copiar código
./mvnw install
Inicie a aplicação Spring Boot:
bash
Copiar código
./mvnw spring-boot:run
Configuração do Frontend
Instalação do Node.js e Vue.js
Instale o Node.js: Siga as instruções em Node.js.

Instale o Vue CLI:

bash
Copiar código
npm install -g @vue/cli
Configuração e Execução do Frontend
Clone o repositório do frontend:
bash
Copiar código
git clone https://github.com/seuusuario/seurepositorio-frontend.git
Instale as dependências:
bash
Copiar código
cd seurepositorio-frontend
npm install
Inicie o servidor de desenvolvimento:
bash
Copiar código
npm run serve
Rodando o Projeto
Para rodar a aplicação completa, você precisará iniciar tanto o backend quanto o frontend.

Inicie o backend seguindo as instruções acima.
Inicie o frontend seguindo as instruções acima.
A aplicação estará disponível no seguinte endereço:

Backend: http://localhost:8081
Frontend: http://localhost:8080
Endpoints da API
Aqui estão alguns dos principais endpoints da API:

GET /api/pessoas: Lista todas as pessoas cadastradas no banco de dados.
POST /api/pessoas: Insere uma nova pessoa e verifica se o CPF é válido.
GET /api/pessoas/{id}: Obtém detalhes de uma pessoa pelo ID.
PUT /api/pessoas/{id}: Atualiza os dados de uma pessoa existente e verifica se o CPF é válido.
DELETE /api/pessoas/{id}: Remove uma pessoa pelo ID.
A documentação da API está disponível em http://localhost:8081/swagger-ui.html.

Contribuindo
Para contribuir com o projeto, siga os seguintes passos:

Fork o repositório.
Crie uma branch para a sua feature (git checkout -b minha-nova-feature).
Faça suas alterações e commit (git commit -am 'Adiciona nova feature').
Push para a branch (git push origin minha-nova-feature).
Abra um Pull Request.
Licença
Este projeto está licenciado sob a Licença XYZ. Veja o arquivo LICENSE para mais detalhes.

Links Úteis
Spring Boot Documentation
Vue.js Documentation
MySQL Documentation
Flyway Documentation
