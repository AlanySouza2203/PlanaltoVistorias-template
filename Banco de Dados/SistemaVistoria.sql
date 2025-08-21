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
    status_agendamento enum("Aberto","Fechado") not null,
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
    resultado enum("Aprovado","Reprovado") not null,
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

-- Inserir cliente de teste
INSERT INTO cliente (nome, cpf, telefone, email, senha) 
VALUES ('João Silva', '123.456.789-00', '(11) 99999-9999', 'joao@email.com', 'senha123');

-- Inserir funcionários de teste
INSERT INTO funcionario (nome, email, matricula, senha, cargo)
VALUES 
('Carlos Vistoriador', 'carlos@empresa.com', '1234', 'senha123', 'Vistoriador'),
('Maria Gerente', 'maria@empresa.com', '123', 'senha123', 'Gerente');
INSERT INTO veiculo (placa,tipo_veiculo,nome_veiculo,modelo,ano_veiculo,chassi,idCliente) VALUES
('000','Moto','Panigale V4 S','Esportivo','2025','0001196',1);
INSERT INTO agendamento (data_agendamento,status_agendamento,hora,idCliente,idVeiculo) VALUES
('2025-08-21','Aberto','12:00',1,1),
('2025-08-21','Fechado','10:00',1,1),
('2025-08-21','Aberto','09:00',1,1);
SELECT * FROM agendamento WHERE status_agendamento = 'Aberto';
-- select * from cliente;
-- select * from veiculo;
-- select * from agendamento;