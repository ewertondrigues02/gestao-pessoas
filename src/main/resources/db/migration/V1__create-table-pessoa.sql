CREATE TABLE tb_pessoa (

    id BIGINT NOT NULL AUTO_INCREMENT,
    nome varchar(50) NOT NULL ,
    cpf char(11) NOT NULL UNIQUE ,
    email varchar(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4