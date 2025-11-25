-- 2. Sistema de Delivery de Comida

CREATE DATABASE Delivery;

SET SQL_SAFE_UPDATES = 0;

USE Delivery;

CREATE TABLE Cliente (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100),
    telefone VARCHAR(15),
    endereco VARCHAR(100)
);

CREATE TABLE Restaurante (
    id_restaurante INT PRIMARY KEY,
    nome VARCHAR(100),
    tipo_cozinha VARCHAR(100),
    telefone VARCHAR(15)
);

CREATE TABLE StatusPedido (
    id_status INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50)
);

CREATE TABLE Pedido (
    id_pedido INT PRIMARY KEY,
    id_cliente INT,
    id_restaurante INT,
    data_hora DATETIME,
    status ENUM('em preparo','a caminho','entregue'),
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente),
    FOREIGN KEY (id_restaurante) REFERENCES Restaurante(id_restaurante),
	id_status INT,
    FOREIGN KEY (id_status) REFERENCES StatusPedido(id_status)
);

CREATE TABLE ItemPedido (
    id_item INT PRIMARY KEY,
    id_pedido INT,
    descricao VARCHAR(100),
    quantidade INT,
    preco DECIMAL(10,2),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(id_pedido)
);