CREATE SEQUENCE usuario_seq START 1 INCREMENT 1;

CREATE TABLE usuario(
    id BIGINT PRIMARY KEY DEFAULT nextval('usuario_seq') NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(20) NOT NULL,
    UNIQUE (email)
);