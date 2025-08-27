# ![DF Vistorias](imagens/logo.gif)

![Java](https://img.shields.io/badge/Java-21-orange?logo=java&logoColor=white)  
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql&logoColor=white)  
![Eclipse IDE](https://img.shields.io/badge/Eclipse-IDE-purple?logo=eclipseide&logoColor=white)  
![Git](https://img.shields.io/badge/Git-Version%20Control-red?logo=git&logoColor=white)  
![Swing](https://img.shields.io/badge/Java-Swing-yellow?logo=coffeescript&logoColor=white)  

Projeto Final de Curso desenvolvido em grupo com o objetivo de aplicar os conhecimentos de Programação Orientada a Objetos (POO), Java e MySQL na construção de um sistema gerencial completo para uma empresa de vistorias veiculares.

---

## 🎯 Objetivos do Projeto  

Ao final deste projeto, fomos capazes de:  

- Analisar e modelar requisitos reais de um sistema empresarial;  
- Projetar um sistema modular utilizando boas práticas de POO;  
- Desenvolver um backend em Java integrado com banco de dados MySQL;  
- Aplicar princípios de persistência de dados e segurança;  
- Trabalhar de forma colaborativa com controle de versão (Git/GitHub);  
- Documentar todo o processo e entregar uma solução funcional;  
- Apresentar e defender a arquitetura e funcionalidades implementadas.  

---

## 🧩 Módulos do Sistema  

O sistema foi dividido em módulos principais, garantindo organização, escalabilidade e clareza do código:  

### ✅ Módulos Implementados  

| Módulo                        | Descrição                                                                                                                                         |
|-------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------|
| **Vistorias**                 | Agendamento e registro completo da vistoria, com geração e emissão de laudos (relatórios).                                                        |
| **Clientes e Veículos**       | Cadastro de clientes e seus respectivos veículos, com histórico de vistorias realizadas.                                                           |
| **Financeiro**                | Controle de pagamentos e registro dos serviços prestados.                                                                                         |
| **Usuários e Permissões**     | Sistema de login, autenticação e autorização, com diferentes perfis de acesso (Ex: Vistoriador, Gerente).                                       |
| **Relatórios**                | Relatórios básicos como: vistorias por período, vistorias por cliente e valores faturados.                                                        |

---

## 🛠️ Tecnologias Utilizadas  

- **Linguagem**: Java (JDK 21)  
- **Banco de Dados**: MySQL  
- **IDE**: Eclipse  
- **Controle de Versão**: Git & GitHub  
- **Interface Gráfica**: Java Swing (Desktop)  

---

## 📂 Estrutura do Projeto  

```
DF_Vistoria/
│
├── src/
│   ├── icones/                     # Ícones da aplicação (menu, botões, etc.)
│   │   ├── add-user.png
│   │   ├── calendario.png
│   │   ├── carro.png
│   │   ├── check.png
│   │   ├── clipboard.png
│   │   ├── dashboard.png
│   │   ├── emitir.png
│   │   ├── file.png
│   │   ├── finance.png
│   │   ├── saida.png
│   │   └── task.png
│   │
│   ├── img/                        # Logo e imagens adicionais
│   │   └── logo.png
│   │
│   ├── Vistoria/
│   │   ├── Main.java                # Classe principal para iniciar o sistema
│   │
│   │   ├── controller/              # Controladores: lógica de negócio
│   │   │   ├── AgendamentoController.java
│   │   │   ├── FuncionarioController.java
│   │   │   └── VeiculoController.java
│   │
│   │   ├── dao/                     # Data Access Object: acesso ao banco
│   │   │   ├── AgendamentoDAO.java
│   │   │   ├── Autenticacao.java
│   │   │   ├── ClienteDAO.java
│   │   │   ├── FuncionarioDAO.java
│   │   │   ├── GerenteDAO.java
│   │   │   ├── UsuarioDAO.java
│   │   │   ├── VeiculoDAO.java
│   │   │   └── VistoriaDAO.java
│   │
│   │   ├── DB/                      # Conexão com o banco de dados
│   │   │   └── Conexao.java
│   │
│   │   ├── model/                   # Classes de modelo (entidades)
│   │   │   ├── Agendamento.java
│   │   │   ├── Cliente.java
│   │   │   ├── Funcionario.java
│   │   │   ├── Veiculo.java
│   │   │   └── Vistoria.java
│   │
│   │   ├── view/                    # Interface gráfica (Java Swing)
│   │   │   ├── AreaGerente.java
│   │   │   ├── CadastroCliente.java
│   │   │   ├── DashboardCliente.java
│   │   │   ├── DashboardGerente.java
│   │   │   ├── DashboardVistoriador.java
│   │   │   ├── Login.java
│   │   │   ├── MenuCliente.java
│   │   │   └── MenuVistoria.java
│   │
│   │   └── module-info.java         # Informações do módulo Java
│
└── Referenced Libraries/            # Bibliotecas externas (MySQL Connector, etc.)
```

---

## 👨‍💻 Desenvolvedores 
- [Cauê Oliveira](https://github.com/Caueoliveira-064) 
- [César Augusto](https://github.com/Cesar0208)
- [Erik Eike](https://github.com/ErikEikeSilva)
- [Fernando Grimello](https://github.com/fernandogrimello)
- [Gabriel Toledo](https://github.com/toledoz)
- [Glauber Maximo](https://github.com/GlauberMaximo)
- [Guilherme Alves](https://github.com/guizera0701)
- [João Vitor Lino](https://github.com/joao2740)
- [João Veiga](https://github.com/joaomvgh)
- [Julio Cesar](https://github.com/Julio5630)
- [Kauã ](https://github.com/Knunesth)
- [Luciana Nascimento](https://github.com/Luciana-Anascimento)
- [Luis Eduardo](https://github.com/xnigthking)
- [Luiz Felipe](https://github.com/luizfelipe90)
- [Marcos](https://github.com/ml2000322)
- [Pedro Matos](https://github.com/PMDL-0310)
- [Ryan Gabriel](https://github.com/Ryan25023110)
- [Tiago Martins](https://github.com/Massacral)
- [William dos Santos Rodrigues](https://github.com/William-Willam)

## 👨‍🏫 Professor  
- [Hudson Neves](https://github.com/HudsonNeves)

# ![DF Vistorias](imagens/parte_do_projeto.gif)
