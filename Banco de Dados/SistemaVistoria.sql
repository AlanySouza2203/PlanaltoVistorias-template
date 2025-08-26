drop database if exists sistema_vistoria;
create database sistema_vistoria;
use sistema_vistoria;

-- =========================
-- CLIENTE
-- =========================
create table cliente (
    idCliente int primary key auto_increment,
    nome varchar(100) not null,
    cpf varchar(15) not null unique,
    telefone varchar(16) not null,
    email varchar(100) not null unique,
    senha varchar(100) not null
);
	
-- =========================
-- FUNCIONARIO
-- =========================
create table funcionario (
    idFuncionario int primary key auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    matricula varchar(100) not null unique,
    senha varchar(100) not null,
    cargo enum("Vistoriador","Gerente") not null
);

-- =========================
-- VEICULO
-- =========================
create table veiculo (
    idVeiculo int primary key auto_increment,
    placa varchar(8) not null unique,
    tipo_veiculo varchar(20) not null, -- alterado
    nome_veiculo varchar(100) not null, -- adicionado
    modelo varchar(100) not null,
    ano_veiculo year not null,
    chassi varchar(17) not null unique,
    observacoes text, -- adicionado
    idCliente int not null, 
    constraint fk_veiculo_cliente foreign key(idCliente) references cliente(idCliente)
);

-- =========================
-- AGENDAMENTO
-- =========================
create table agendamento (
    idAgendamento int primary key auto_increment,
    data_agendamento date not null,
    status_agendamento enum("Concluido","Cancelado","Pendente") not null,
    hora time not null,
    idCliente int not null,
    idVeiculo int not null,
    constraint fk_agendamento_cliente foreign key(idCliente) references cliente(idCliente),
    constraint fk_agendamento_veiculo foreign key(idVeiculo) references veiculo(idVeiculo)
);

-- =========================
-- VISTORIA
-- =========================
create table vistoria (
    idVistoria int primary key auto_increment,
    data_vistoria date not null,
    resultado enum("Aprovado","Reprovado","Aprovado com ressalvas") not null,
    status_pagamento enum("Pendente","Pago") not null,
    observacoes text,
    idAgendamento int not null,
    idFuncionario int not null,
    constraint fk_vistoria_agendamento foreign key(idAgendamento) references agendamento(idAgendamento),
    constraint fk_vistoria_funcionario foreign key(idFuncionario) references funcionario(idFuncionario)
);

-- =========================
-- PAGAMENTO
-- =========================
create table pagamento (
    idPagamento int primary key auto_increment,
    forma_pagamento enum("Débito","Crédito","Pix","Boleto","Dinheiro") not null,
    valor decimal(10,2) not null,
    data_pagamento date not null,
    idAgendamento int not null,
    constraint fk_pagamento_agendamento foreign key (idAgendamento) references agendamento(idAgendamento)
);

-- ==============================
-- Não Apague as modificações
-- ==============================
ALTER TABLE agendamento
MODIFY COLUMN status_agendamento VARCHAR(20) NOT NULL;

-- ==============================
-- insert de funcionario gerente
-- ==============================
-- Insere um funcionário com o cargo de Vistoriador
INSERT INTO funcionario (nome, email, matricula, senha, cargo)
VALUES ('Testador Vistoria', 'teste.vistoria@empresa.com', 'VIS-001', 'senha123', 'Vistoriador');

-- Insere um funcionário com o cargo de Gerente
INSERT INTO funcionario (nome, email, matricula, senha, cargo)
VALUES ('Testador Gerente', 'teste.gerente@empresa.com', 'GER-001', 'senha456', 'Gerente');

-- ==============================
-- Verificando os armazenamentos
-- ==============================
select * from cliente;
select * from veiculo;
select * from agendamento;

SELECT COUNT(*) FROM agendamento WHERE status_agendamento = 'Agendado';




-- 1. Inserir um novo cliente na tabela 'cliente'
-- idCliente será gerado automaticamente.
INSERT INTO cliente (nome, cpf, telefone, email, senha)
VALUES ('Maria Silva', '123.456.789-01', '(11) 98765-4321', 'maria.silva@email.com', 'senha_segura123');

-- 2. Inserir um novo veículo na tabela 'veiculo'
-- A placa e o chassi devem ser únicos.
-- Usamos o 'idCliente' inserido acima.
INSERT INTO veiculo (placa, tipo_veiculo, nome_veiculo, modelo, ano_veiculo, chassi, observacoes, idCliente)
VALUES ('ABC-1234', 'Carro', 'Ford Fusion', 'Titanium', 2018, 'ABC12345DEF678901', 'Pequenos arranhões na porta do motorista.', 1);

-- 3. Inserir três novos agendamentos na tabela 'agendamento'
-- Todos os agendamentos se referem ao cliente e veículo inseridos nas etapas anteriores.
-- Os status são definidos como 'Agendado', 'Fechado' e 'Pendente' para demonstrar as opções.
-- O campo idCliente e idVeiculo devem corresponder aos IDs existentes.

-- Agendamento 1: status "Agendado"
INSERT INTO agendamento (data_agendamento, status_agendamento, hora, idCliente, idVeiculo)
VALUES ('2025-09-01', 'Pendente', '10:30:00', 1, 1);

-- Agendamento 2: status "Fechado"
INSERT INTO agendamento (data_agendamento, status_agendamento, hora, idCliente, idVeiculo)
VALUES ('2025-08-28', 'Pendente', '14:00:00', 1, 1);

-- Agendamento 3: status "Pendente"
INSERT INTO agendamento (data_agendamento, status_agendamento, hora, idCliente, idVeiculo)
VALUES ('2025-09-15', 'Pendente', '09:00:00', 1, 1);