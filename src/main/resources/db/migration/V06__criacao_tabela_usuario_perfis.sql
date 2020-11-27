CREATE TABLE usuario(
	codigo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(80) NOT NULL UNIQUE,
    senha VARCHAR(8) NOT NULL
);

CREATE TABLE perfil(
	codigo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE usuario_perfil(
	codigo_usuario INT NOT NULL,
    codigo_perfil INT NOT NULL,
    PRIMARY KEY(codigo_usuario,codigo_perfil),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_perfil) REFERENCES perfil (codigo)
);

