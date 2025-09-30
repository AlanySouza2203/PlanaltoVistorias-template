create database  Planalto_Vistoria;
use Planalto_Vistoria; 

create table Clientes (
Id_Clientes int primary key auto_increment,
Nome varchar (150) not null,
Cpf varchar (11) not null,
Telefone varchar (11) not null,
Email varchar (255) not null
);
create table  Funcionarios (
Id_Funcionarios int primary key auto_increment,
Nome varchar (150) not null,
Cpf varchar (11) not null,
Cargo varchar (50) not null,
Telefone varchar (11) not null
);
create table Veiculos(
Id_Veiculos int primary key auto_increment,
Placa varchar(8) not null,
Marca varchar (50) not null,
Modelo varchar (80) not null,
Ano varchar (5) not null,
Numero_chassi varchar (17) not null,
Id_Clientes int not null,  -- referencia Id clientes  para definir chave estrangeira
foreign key(Id_Clientes) references Clientes(Id_Clientes)
);
create table Agendamento(
Id_Agendamento int primary key auto_increment,
Id_Veiculos int not null,
Id_Funcionarios int not null,
Id_Clientes int not null,
Data_Agendamento datetime not null,
Tipo_Servico varchar (150) not null,
foreign key (Id_Veiculos) references Veiculos(Id_Veiculos),
foreign key (Id_Funcionarios) references Funcionarios(Id_Funcionarios),
foreign key (Id_Clientes) references Clientes(Id_Clientes)
);
create table Vistoria  (
Id_Vistoria int primary key auto_increment,
Id_Funcionarios int not null,
Id_Agendamento int not null,
Data_vistoria  datetime not null,
Itens_Verificados text,
Observacao text,
foreign key (Id_Funcionarios) references Funcionarios(Id_Funcionarios),
foreign key (Id_Agendamento) references Agendamento(Id_Agendamento)
);
create table Pagamento (
Id_Clientes int not null,
Id_Vistoria int not null,
Valor decimal(10,2) not null,
Data_Pagamento datetime not null,
Forma_Pagamento  enum ('Dinheiro', 'Cartao Debito', 'Cartao Credito', 'Pix')not null,
Status_Pagamento enum ('Pendente', 'Pago', 'Cancelado')  default 'Pendente',
foreign key (Id_Clientes) references Clientes (Id_Clientes),
foreign key (Id_Vistoria) references Vistoria (Id_Vistoria)
);

alter table Clientes add constraint Uk_Clientes_Cpf UNIQUE (Cpf);
alter table Funcionarios add constraint Uk_Funcionarios_Cpf UNIQUE (Cpf);
alter table Veiculos add constraint Uk_Veiculos_Placa UNIQUE (Placa);
alter table Veiculos add constraint Uk_Veiculos_chassi UNIQUE (Numero_chassi);


create index idx_Clientes_Cpf on Clientes(Cpf);
create index idx_Veiculos_Placa on Veiculos(Placa);
create index idx_Agendamento_Data on Agendamento(Data_Agendamento);
create index idx_Vistoria_Data  on Vistoria(Data_vistoria);

