CREATE TABLE pessoa(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(50) NOT NULL,
	endereco BIGINT(20) NOT NULL,
    ativo TINYINT(1), 
    
    FOREIGN KEY (endereco)
	REFERENCES endereco(codigo)
) 
ENGINE=InnoDB 
DEFAULT CHARSET=utf8;