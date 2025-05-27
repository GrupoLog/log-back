USE GrupoLog_DB;

-- Tabela Produtos
CREATE TABLE Produtos (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    peso INT CHECK (peso > 0) NOT NULL,
    data_validade DATE,
    descricao VARCHAR(255)
);

-- Tabela Clientes
CREATE TABLE Clientes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    sobrenome VARCHAR(50) NOT NULL,
    rua VARCHAR(100) NOT NULL,
    bairro VARCHAR(100) NOT NULL,
    numero INT CHECK (numero > 0) NOT NULL,
    cidade VARCHAR(30) DEFAULT 'Recife' NOT NULL
);

-- Tabela Telefone
CREATE TABLE Telefone (
    telefone VARCHAR(11) PRIMARY KEY,
    clientes_cpf VARCHAR(11) NOT NULL,
    FOREIGN KEY (clientes_cpf) REFERENCES Clientes (cpf)
);

-- Tabela Veiculo
CREATE TABLE Veiculo (
    chassi VARCHAR(17) PRIMARY KEY,
    proprietario VARCHAR(20) CHECK (proprietario IN ('Proprio', 'Terceirizado')) NOT NULL,
    placa VARCHAR(7) UNIQUE NOT NULL
);

-- Subclasse Moto
    CREATE TABLE Moto (
    veiculo_chassi VARCHAR(17) PRIMARY KEY,
    cap_carga INT CHECK (cap_carga > 0) NOT NULL,
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Subclasse Van
    CREATE TABLE Van (
    veiculo_chassi VARCHAR(17) PRIMARY KEY,
    cap_passageiros INT CHECK (cap_passageiros >= 1) NOT NULL,
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela Motoristas
    CREATE TABLE Motoristas (
    cnh VARCHAR(11) PRIMARY KEY,
    tipo_cnh VARCHAR(3) CHECK (tipo_cnh IN ('A', 'B', 'AB', 'C', 'D', 'E')) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    nome VARCHAR(30) NOT NULL,
    tipo VARCHAR(20) CHECK (tipo IN ('Fixo', 'Terceirizado')) NOT NULL,
    telefone_um VARCHAR(11) NOT NULL, -- Tem que ter pelo menos 1 telefone cadastrado
    telefone_dois VARCHAR(11),
    cnh_supervisionado VARCHAR(11),
    FOREIGN KEY (cnh_supervisionado) REFERENCES Motoristas (cnh)
);

-- Tabela pode_Conduzir
CREATE TABLE Pode_Conduzir (
    motoristas_cnh VARCHAR(11) NOT NULL,
    veiculo_chassi VARCHAR(17) NOT NULL,
    PRIMARY KEY (motoristas_cnh, veiculo_chassi),
    FOREIGN KEY (motoristas_cnh) REFERENCES Motoristas (cnh),
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela viagem
CREATE TABLE Viagem (
    id_viagem INT PRIMARY KEY AUTO_INCREMENT,
    data_viagem DATE NOT NULL,
    hora_viagem TIME NOT NULL,
    origem VARCHAR(30) NOT NULL,
    destino VARCHAR(30) NOT NULL,
    veiculo_chassi VARCHAR(17) NOT NULL,
    motoristas_cnh VARCHAR(11) NOT NULL,
    FOREIGN KEY (motoristas_cnh) REFERENCES Motoristas (cnh),
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela Servicos (superclasse para entrega e transporte)
    CREATE TABLE Servicos (
    id_servico INT PRIMARY KEY AUTO_INCREMENT,
    id_viagem INT NOT NULL,
    FOREIGN KEY (id_viagem) REFERENCES Viagem (id_viagem)

);

-- Subclasse Servico_entrega (herda de Servicos)
    CREATE TABLE Servico_Entrega (
    id_servico INT PRIMARY KEY,
    destinatario VARCHAR(60) NOT NULL,
    peso_total INT CHECK (peso_total > 0) NOT NULL,
    descricao_produto VARCHAR(255),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);

-- Subclasse Servico_transporte (herda de Servicos)
    CREATE TABLE Servico_Transporte (
    id_servico INT PRIMARY KEY,
    qtd_passageiros INT CHECK (qtd_passageiros BETWEEN 1 AND 20) NOT NULL,
    descricao_transporte VARCHAR(255),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);

-- Tabela Solicitacoes
    CREATE TABLE Solicitacoes (
    id_solicitacao INT PRIMARY KEY AUTO_INCREMENT,
    data_solicitacao DATE DEFAULT (CURDATE()) NOT NULL,
    forma_pagamento VARCHAR(20) CHECK (forma_pagamento IN ('Pix', 'Cartao', 'Dinheiro')) NOT NULL,
    valor_pagamento DOUBLE CHECK (valor_pagamento >= 0) NOT NULL,
    status_pagamento VARCHAR(20) DEFAULT 'Pendente' NOT NULL,
    clientes_cpf VARCHAR(11) NOT NULL,
    id_servico INT NOT NULL,
    FOREIGN KEY (clientes_cpf) REFERENCES Clientes (cpf),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);

-- Tabela Contem
    CREATE TABLE Contem (
    id_produto INT,
    id_solicitacao INT,
    PRIMARY KEY (id_produto, id_solicitacao),
    FOREIGN KEY (id_solicitacao) REFERENCES Solicitacoes (id_solicitacao),
    FOREIGN KEY (id_produto) REFERENCES Produtos (id_produto)
);

-- Tabela Log_Clientes
CREATE TABLE Log_Clientes (
    id_log INT AUTO_INCREMENT PRIMARY KEY,
    cpf VARCHAR(11),
    nome VARCHAR(100),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trigger para log de novos clientes
DROP TRIGGER IF EXISTS trg_log_novo_cliente;

CREATE TRIGGER trg_log_novo_cliente
AFTER INSERT ON Clientes
FOR EACH ROW
INSERT INTO Log_Clientes (cpf, nome)
VALUES (NEW.cpf, NEW.nome);

-- Procedure para contar viagens por tipo de veiculo
DROP PROCEDURE IF EXISTS sp_contar_viagens_por_tipo;

CREATE PROCEDURE sp_contar_viagens_por_tipo(IN ano INT)
SELECT 'van' AS tipo, COUNT(*) AS total
FROM Viagem vi
JOIN Van van ON vi.veiculo_chassi = van.veiculo_chassi
WHERE YEAR(vi.data_viagem) = ano
UNION ALL
SELECT 'moto' AS tipo, COUNT(*) AS total
FROM Viagem vi
JOIN Moto moto ON vi.veiculo_chassi = moto.veiculo_chassi
WHERE YEAR(vi.data_viagem) = ano;