CREATE TABLE lancamento (
	codigo INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(80) NOT NULL,
    dataVencimento DATE NOT NULL,
    dataPagamento DATE NOT NULL,
    valor DECIMAL(6,2) NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    observacao VARCHAR(100) NOT NULL,
    codigo_pessoa INT NOT NULL,
    codigo_categoria INT NOT NULL,
    FOREIGN KEY (codigo_pessoa) REFERENCES pessoa (codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo)
);