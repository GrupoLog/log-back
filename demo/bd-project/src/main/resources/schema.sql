USE GrupoLog_DB;

-- Tabela Produtos
CREATE TABLE Produtos (
    id_produto INT PRIMARY KEY AUTO_INCREMENT,
    peso INT CHECK (peso > 0),
    data_validade DATE,
    descricao VARCHAR(255)
);

-- Tabela Clientes
   CREATE TABLE Clientes (
    cpf VARCHAR(11) PRIMARY KEY,
    nome VARCHAR(30) NOT NULL,
    sobrenome VARCHAR(30),
    rua VARCHAR(30)NOT NULL,
    bairro VARCHAR(30) NOT NULL,
    numero INT CHECK (numero > 0),
    cidade VARCHAR(30) DEFAULT 'Recife'
);

-- Tabela Telefone
    CREATE TABLE Telefone (
    telefone VARCHAR(11) PRIMARY KEY,
    clientes_cpf VARCHAR(11),
    FOREIGN KEY (clientes_cpf) REFERENCES Clientes (cpf)
);

-- Tabela Veiculo
CREATE TABLE Veiculo (
    chassi VARCHAR(17) PRIMARY KEY,
    proprietario VARCHAR(20) CHECK (proprietario IN ('Proprio', 'Terceirizado')),
    placa VARCHAR(7) UNIQUE
);

-- Subclasse Moto
    CREATE TABLE Moto (
    veiculo_chassi VARCHAR(17) PRIMARY KEY,
    cap_carga INT CHECK (cap_carga > 0),
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Subclasse Van
    CREATE TABLE Van (
    veiculo_chassi VARCHAR(17) PRIMARY KEY,
    cap_passageiros INT CHECK (cap_passageiros >= 1),
    FOREIGN KEY (veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela Motoristas
    CREATE TABLE Motoristas (
    cnh VARCHAR(11) PRIMARY KEY,
    tipo_cnh VARCHAR(3) CHECK (tipo_cnh IN ('A', 'B', 'AB', 'C', 'D', 'E')),
    cpf VARCHAR(11),
    nome VARCHAR(30),
    tipo VARCHAR(20) CHECK (tipo IN ('Fixo', 'Terceirizado')),
    telefone_um VARCHAR(11) NOT NULL, -- Tem que ter pelo menos 1 telefone cadastrado
    telefone_dois VARCHAR(11),
    cnh_supervisionado VARCHAR(11),
    FOREIGN KEY (cnh_supervisionado) REFERENCES Motoristas (cnh)
);

-- Tabela pode_Conduzir
CREATE TABLE pode_Conduzir (
    fk_Motoristas_cnh VARCHAR(11),
    fk_veiculo_chassi VARCHAR(17),
    PRIMARY KEY (fk_Motoristas_cnh, fk_veiculo_chassi),
    FOREIGN KEY (fk_Motoristas_cnh) REFERENCES Motoristas (cnh),
    FOREIGN KEY (fk_veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela viagem
CREATE TABLE viagem (
    id_viagem INT PRIMARY KEY AUTO_INCREMENT,
    data_viagem DATE,
    hora_viagem TIME,
    origem VARCHAR(30) NOT NULL,
    destino VARCHAR(30) NOT NULL,
    fk_veiculo_chassi VARCHAR(17),
    fk_Motoristas_cnh VARCHAR(11),
    FOREIGN KEY (fk_Motoristas_cnh) REFERENCES Motoristas (cnh),
    FOREIGN KEY (fk_veiculo_chassi) REFERENCES Veiculo (chassi)
);

-- Tabela Servicos (superclasse para entrega e transporte)
    CREATE TABLE Servicos (
    id_servico INT PRIMARY KEY AUTO_INCREMENT,
    fk_viagem_id_viagem INT,
    FOREIGN KEY (fk_viagem_id_viagem) REFERENCES viagem (id_viagem)

);

-- Subclasse Servico_entrega (herda de Servicos)
    CREATE TABLE Servico_entrega (
    id_servico INT PRIMARY KEY,
    destinatario VARCHAR(60) NOT NULL,
    peso_total DOUBLE CHECK (peso_total > 0),
    descricao_produto VARCHAR(255),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);

-- Subclasse Servico_transporte (herda de Servicos)
    CREATE TABLE Servico_transporte (
    id_servico INT PRIMARY KEY,
    qtd_passageiros INT CHECK (qtd_passageiros BETWEEN 1 AND 20),
    descricao_transporte VARCHAR(255),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);

-- Tabela Solicitacoes
    CREATE TABLE Solicitacoes (
    id_solicitacao INT PRIMARY KEY AUTO_INCREMENT,
    data_solicitacao DATE DEFAULT (CURDATE()),
    forma_pagamento VARCHAR(20) CHECK (forma_pagamento IN ('Pix', 'Cartao', 'Dinheiro')),
    valor_pagamento DOUBLE CHECK (valor_pagamento >= 0),
    status_pagamento VARCHAR(20) DEFAULT 'Pendente',
    id_produto INT,
    clientes_cpf VARCHAR(11),
    id_servico INT,
    FOREIGN KEY (id_produto) REFERENCES Produtos (id_produto),
    FOREIGN KEY (clientes_cpf) REFERENCES Clientes (cpf),
    FOREIGN KEY (id_servico) REFERENCES Servicos (id_servico)
);