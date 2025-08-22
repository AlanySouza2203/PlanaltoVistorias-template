drop database if exists sistema_vistoria_ajustado;
create database sistema_vistoria_ajustado;
use sistema_vistoria_ajustado;

-- Tabela de Cliente
create table cliente (
    idCliente int primary key auto_increment,
    nome varchar(100) not null,
    cpf varchar(15) not null unique,
    telefone varchar(16) not null,
    email varchar(100) not null unique,
    senha varchar(255) not null -- Alterado para senha criptografada
);

-- Tabela de Endereço do Cliente (nova)
create table endereco_cliente (
    idEndereco int primary key auto_increment,
    idCliente int not null,
    rua varchar(100) not null,
    cidade varchar(100) not null,
    estado char(2) not null,
    cep varchar(10) not null,
    constraint fk_endereco_cliente foreign key(idCliente) references cliente(idCliente)
);

-- Tabela de Funcionário
create table funcionario (
    idFuncionario int primary key auto_increment,
    nome varchar(100) not null,
    email varchar(100) not null unique,
    matricula varchar(100) not null unique,
    senha varchar(255) not null, -- Alterado para senha criptografada
    cargo enum("Vistoriador","Gerente") not null
);

-- Tabela de Veículo
create table veiculo (
    idVeiculo int primary key auto_increment,
    placa varchar(8) not null unique,
    tipo_veiculo varchar(20) not null,
    nome_veiculo varchar(100) not null,
    modelo varchar(100) not null,
    ano_veiculo year not null,
    chassi varchar(17) not null unique,
    observacoes text,
    idCliente int not null, 
    constraint fk_veiculo_cliente foreign key(idCliente) references cliente(idCliente)
);

-- Tabela de Agendamento
create table agendamento (
    idAgendamento int primary key auto_increment,
    data_agendamento date not null,
    hora time not null,
    status_agendamento enum("Agendado", "Concluido", "Cancelado") not null, -- Status mais claro
    idCliente int not null,
    idVeiculo int not null,
    constraint fk_agendamento_cliente foreign key(idCliente) references cliente(idCliente),
    constraint fk_agendamento_veiculo foreign key(idVeiculo) references veiculo(idVeiculo)
);

-- Tabela de Vistoria
create table vistoria (
    idVistoria int primary key auto_increment,
    data_vistoria date not null,
    resultado enum("Aprovado","Reprovado", "Aprovado com ressalvas") not null, -- Adicionado novo status
    observacoes text,
    idAgendamento int not null,
    idFuncionario int not null,
    constraint fk_vistoria_agendamento foreign key(idAgendamento) references agendamento(idAgendamento),
    constraint fk_vistoria_funcionario foreign key(idFuncionario) references funcionario(idFuncionario)
);

-- Tabela de Itens da Vistoria (nova)
create table item_vistoria (
    idItemVistoria int primary key auto_increment,
    idVistoria int not null,
    nome_item varchar(100) not null,
    status_item enum("Aprovado", "Reprovado", "Não se aplica") not null,
    observacoes_item text,
    constraint fk_item_vistoria foreign key(idVistoria) references vistoria(idVistoria)
);

-- Tabela de Pagamento
create table pagamento (
    idPagamento int primary key auto_increment,
    forma_pagamento enum("Débito","Crédito","Pix","Boleto","Dinheiro") not null,
    valor decimal(10,2) not null,
    data_pagamento date not null,
    idVistoria int not null, -- Vinculado à Vistoria
    constraint fk_pagamento_vistoria foreign key (idVistoria) references vistoria(idVistoria)
);

select * from cliente;

UPDATE Cliente
SET
    cpf = '25836914700',
    telefone = 'testando@teste',
    email = 'teste123@teste.com' -- Assumindo que o e-mail real seria esse
WHERE
    idCliente = 1;