USE GrupoLog_DB;

-- Tabela Produtos
INSERT INTO Produtos (peso, data_validade, descricao) VALUES
(1000, '2025-08-10', 'Arroz branco tipo 1 - pacote 1kg'),
(500, '2025-06-15', 'Açúcar refinado - pacote 500g'),
(2000, '2026-01-01', 'Feijão preto - pacote 2kg'),
(1500, '2025-05-30', 'Macarrão espaguete - pacote 1,5kg'),
(250, '2025-04-25', 'Fermento químico em pó - pote 250g'),
(750, '2025-07-20', 'Café torrado e moído - pacote 750g'),
(1200, '2025-12-31', 'Farinha de trigo - pacote 1,2kg'),
(300, '2025-05-05', 'Leite em pó integral - pacote 300g');


-- Tabela Clientes
INSERT INTO Clientes (CPF, nome, sobrenome, rua, bairro, numero, cidade) VALUES
('12345678901', 'Lucas', 'Pereira', 'Rua das Palmeiras', 'Boa Viagem', 120, 'Recife'),
('23456789012', 'Mariana', 'Alves', 'Avenida Brasil', 'Casa Forte', 450, 'Recife'),
('34567890123', 'José', 'Silva', 'Rua do Sol', 'Espinheiro', 78, 'Recife'),
('45678901234', 'Ana', 'Oliveira', 'Rua das Acácias', 'Graças', 210, 'Recife'),
('56789012345', 'Carlos', 'Moura', 'Rua Flor de Lis', 'Pina', 35, 'Recife'),
('67890123456', 'Fernanda', 'Costa', 'Rua do Sossego', 'Derby', 99, 'Recife'),
('78901234567', 'Rafael', 'Souza', 'Rua Verde', 'Várzea', 11, 'Recife'),
('89012345678', 'Juliana', 'Nascimento', 'Rua do Lazer', 'Madalena', 150, 'Recife');



-- Tabela Telefone
INSERT INTO Telefone (telefone, Clientes_CPF) VALUES
('81999990001', '12345678901'),
('81999990002', '23456789012'),
('81999990003', '34567890123'),
('81999990006', '67890123456'),
('81999990007', '78901234567'),
('81999990008', '89012345678');


-- Tabela Veiculo
INSERT INTO Veiculo (chassi, proprietario, placa) VALUES
('9BWZZZ377VT004251', 'Proprio', 'ABC1D23'),
('9BWZZZ377VT004252', 'Terceirizado', 'XYZ2E34'),
('9BWZZZ377VT004253', 'Proprio', 'DEF3F45'),
('9BWZZZ377VT004254', 'Terceirizado', 'GHI4G56'),
('9BWZZZ377VT004255', 'Proprio', 'JKL5H67');


-- Subclasse Moto
INSERT INTO Moto (veiculo_chassi, cap_carga) VALUES
('9BWZZZ377VT004251', 50),
('9BWZZZ377VT004253', 65);



-- Subclasse Van
INSERT INTO Van (veiculo_chassi, cap_passageiros) VALUES
('9BWZZZ377VT004252', 12),
('9BWZZZ377VT004254', 15),
('9BWZZZ377VT004255', 18);


-- Tabela Motoristas
INSERT INTO Motoristas (CNH, tipo_cnh, CPF, nome, tipo, telefone_um, telefone_dois, cnh_supervisionado) VALUES
('12345678900', 'AB', '11122233344', 'João Silva', 'Fixo', '81999990000', '81999990001', NULL),
('98765432100', 'D', '55566677788', 'Maria Costa', 'Fixo', '81988880000', '81988880001', NULL),
('12312312300', 'B', '99988877766', 'Carlos Lima', 'Terceirizado', '81977770000', '81977770001', '12345678900'),
('32132132100', 'C', '44455566677', 'Ana Souza', 'Terceirizado', '81966660000', '81966660001', '12345678900'),
('45645645600', 'E', '33322211100', 'Paulo Mendes', 'Fixo', '81955550000', '81955550001', '98765432100');



-- Tabela pode_Conduzir
INSERT INTO pode_Conduzir (fk_Motoristas_CNH, fk_veiculo_chassi) VALUES
('12345678900', '9BWZZZ377VT004251'),
('12345678900', '9BWZZZ377VT004253'),
('12312312300', '9BWZZZ377VT004252'),
('32132132100', '9BWZZZ377VT004254'),
('45645645600', '9BWZZZ377VT004251');


-- Fazer a partir daqui

-- Tabela viagem
INSERT INTO viagem (data_viagem, hora_viagem, origem, destino, fk_veiculo_chassi, fk_Motoristas_CNH) VALUES
('2025-04-20', '08:00:00', 'Recife', 'Olinda', '9BWZZZ377VT004251', '12345678900'),
('2025-04-21', '09:30:00', 'Recife', 'Jaboatão', '9BWZZZ377VT004253', '12345678900'),
('2025-04-21', '10:15:00', 'Olinda', 'Paulista', '9BWZZZ377VT004255', '98765432100'),
('2025-04-22', '11:45:00', 'Camaragibe', 'Recife', '9BWZZZ377VT004252', '12312312300'),
('2025-04-22', '14:00:00', 'Jaboatão', 'Cabo de Santo Agostinho', '9BWZZZ377VT004254', '12312312300'),
('2025-04-23', '07:15:00', 'Paulista', 'Abreu e Lima', '9BWZZZ377VT004254', '32132132100'),
('2025-04-23', '15:45:00', 'Recife', 'Caruaru', '9BWZZZ377VT004251', '45645645600'),
('2025-04-24', '13:30:00', 'Recife', 'Garanhuns', '9BWZZZ377VT004252', '45645645600');


-- Tabela Servicos (superclasse para entrega e transporte)
INSERT INTO Servicos (fk_viagem_id_viagem) VALUES
(1),
(2),
(3),
(4),
(5);

-- Subclasse Servico_entrega (herda de Servicos)
INSERT INTO Servico_entrega (id_servico, destinatario, peso_total, descricao_produto) VALUES
(1, 'Maria Silva', 20.5, 'Caixa de eletrônicos'),
(3, 'Ana Oliveira', 50.0, 'Móveis para escritório'),
(5, 'Luiza Martins', 25.0, 'Equipamentos de informática');

-- Subclasse Servico_transporte (herda de Servicos)
INSERT INTO Servico_transporte (id_servico, qtd_passageiros, descricao_transporte) VALUES
(2, 3, 'Transporte de delegação esportiva'),
(4, 2, 'Transporte de executivos para reunião');

-- Tabela Solicitacoes
INSERT INTO Solicitacoes (forma_pagamento, valor_pagamento, id_produto, Clientes_CPF, id_servico) VALUES
('Pix', 30.00, NULL, '12345678901', 1),
('Cartao', 12.50, 1, '23456789012', 4),
('Pix', 5.00, 5, '45678901234', 2),
('Cartao', 3.50, NULL, '56789012345', 5);