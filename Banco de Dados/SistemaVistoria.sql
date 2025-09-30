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
Numero_chassi varchar (17) not null
);

create table Agendamento(
Id_Agendamento int primary key auto_increment,
Id_Veiculos int not null,
Id_Funcionarios int not null,
Id_Cliente int not null,
Data_Agendamento datetime not null,
Tipo_Servico varchar (150) not null
);

create table Vistoria  (
Id_Vistoria int primary key auto_increment,
Id_Funcionarios int not null,
Data_vistoria  datetime not null,
Itens_Verificados text,
Observacao text
);

create table Pagamento (
Id_Clientes int not null,
Valor decimal(10,2) not null,
Data_Pagamento datetime not null,
Forma_Pagamento  enum ('Dinheiro', 'Cartao Debito', 'Cartao Credito', 'Pix')not null,
Status_Pagamento enum ('Pendente', 'Pago', 'Cancelado')  default 'Pendente'
);


