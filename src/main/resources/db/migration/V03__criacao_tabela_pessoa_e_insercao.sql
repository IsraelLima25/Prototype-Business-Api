CREATE TABLE pessoa(
	codigo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL,
    logradouro VARCHAR(50) NOT NULL,
    numero VARCHAR(5) NOT NULL,
    complemento VARCHAR(50) NOT NULL,
    bairro VARCHAR(30) NOT NULL,
    cep VARCHAR(8) NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    estado VARCHAR(3) NOT NULL
);

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Israel Lima", 1, "Rua das rosas", 10, "primeiro andar", "Pituba","12345678", "Salvador", "BA");

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Ronald Fonseca", 0, "Rua das pedras", 25, "terreo", "Barra da Tijuca", "45698712", "Rio de Janeiro", "RJ");

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Luciana Medereiros", 1, "Rua Otavio silva", 25, "primeiro andar", "Corredor da Vitória", "12639874", "Salvador", "BA");

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Eduardo Lima", 1, "Rua do fogo", 35, "casa", "Ribeira", "45963685", "Salvador", "BA");

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Carmen Santos", 1, "Av 25 de março", 40, "quinto andar", "centro", "12369143", "São Paulo", "SP");

INSERT INTO pessoa (nome,ativo,logradouro,numero,complemento,bairro,cep,cidade,estado) 
VALUES ("Valdir Medeiros", 0, "Rua das rosas", 48, "quarto andar", "Pituba", "12698437", "Salvador", "BA");