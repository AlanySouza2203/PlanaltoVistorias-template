# ![DF Vistorias](imagens/logo.gif)

![Java](https://img.shields.io/badge/Java-21-orange?logo=java&logoColor=white)  
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?logo=mysql&logoColor=white)  
![Eclipse IDE](https://img.shields.io/badge/Eclipse-IDE-purple?logo=eclipseide&logoColor=white)  
![Git](https://img.shields.io/badge/Git-Version%20Control-red?logo=git&logoColor=white)  
![Swing](https://img.shields.io/badge/Java-Swing-yellow?logo=coffeescript&logoColor=white)  

---

## 🚗 DF Vistorias – Sistema Gerencial de Vistorias Veiculares

Projeto Final de Curso desenvolvido em grupo, com o objetivo de aplicar os principais conceitos de Programação Orientada a Objetos (POO), Java e MySQL na criação de uma solução completa para gestão de vistorias automotivas.

---

### 🎯 Principais Objetivos

- Modelar requisitos reais de sistemas empresariais;
- Projetar arquitetura modular e escalável com boas práticas de POO;
- Desenvolver backend Java integrado ao banco de dados MySQL;
- Aplicar persistência de dados e princípios de segurança;
- Colaborar utilizando Git/GitHub para versionamento;
- Documentar e apresentar o processo e solução final.

---

## 🧩 Módulos do Sistema

O sistema é organizado em módulos para facilitar manutenção, escalabilidade e clareza do código:

| Módulo                        | Descrição                                                                                                                                             |
|-------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Vistorias**                 | Agendamento, registro e emissão de laudos detalhados de vistoria.                                                                                     |
| **Clientes e Veículos**       | Cadastro de clientes e veículos, histórico de vistorias e consultas.                                                                                  |
| **Financeiro**                | Controle financeiro, pagamentos, registro de serviços e faturamento.                                                                                  |
| **Usuários e Permissões**     | Login seguro, autenticação, perfis de acesso diferenciados (Ex: Vistoriador, Gerente).                                                                |
| **Relatórios**                | Relatórios gerenciais: vistorias por período, por cliente, faturamento, etc.                                                                          |

---

## 🛠️ Tecnologias Utilizadas

- **Java (JDK 21)**  
- **MySQL**  
- **Eclipse IDE**  
- **Git & GitHub**  
- **Java Swing (Desktop GUI)**  

---

## 📂 Estrutura do Projeto

```
DF_Vistoria/
│
├── src/
│   ├── icones/                     # Ícones para menus e botões
│   ├── img/                        # Logo e imagens do sistema
│   ├── Vistoria/
│   │   ├── Main.java               # Classe principal
│   │   ├── controller/             # Lógica de negócio
│   │   ├── dao/                    # Acesso ao banco de dados (JDBC)
│   │   ├── DB/                     # Configuração de conexão MySQL
│   │   ├── model/                  # Entidades e modelos
│   │   ├── view/                   # Interfaces gráficas (Swing)
│   │   └── module-info.java        # Módulo Java
│
└── Referenced Libraries/           # Bibliotecas externas (MySQL Connector)
```

---

## ⚡ Persistência de Dados com MySQL (JDBC)

O sistema utiliza **JDBC** para integração com o banco **MySQL**, garantindo persistência, integridade e segurança das informações.

### 📦 Configuração do JDBC

1. **Estrutura recomendada:**
    ```
    /SeuProjeto
     └── Conexao/
         └── ConexaoSQL.java
    ```

2. **Exemplo de Conexão Java:**
    ```java
    package Vistoria.Conexao;
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.SQLException;

    public class ConexaoSQL {
        private static final String URL = "jdbc:mysql://localhost:3306/seu_banco";
        private static final String USER = "seu_usuario";
        private static final String PASSWORD = "sua_senha";

        public static Connection getConnection() {
            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                System.out.println("Erro ao conectar: " + e.getMessage());
                return null;
            }
        }

        public static void main(String[] args) {
            Connection conn = getConnection();
            if (conn != null) {
                System.out.println("Conexão realizada com sucesso!");
                try {
                    conn.close();
                    System.out.println("Conexão encerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    ```

3. **Adicione o Driver JDBC ao Projeto:**

    - **Maven:**
        ```xml
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
        ```
    - **Manual (.jar):**  
        Baixe o `mysql-connector-java` e adicione ao classpath do projeto.

4. **Testando a conexão:**  
    - Configure a `URL`, `USER` e `PASSWORD` no `ConexaoSQL.java`.
    - Execute o método `main` para validar.

---

## 📚 Referências

- [Documentação JDBC](https://docs.oracle.com/javase/tutorial/jdbc/)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

---

## 👨‍💻 Equipe de Desenvolvimento

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
- [Kauã Thierry](https://github.com/Knunesth)
- [Luciana Nascimento](https://github.com/Luciana-Anascimento)
- [Luis Eduardo](https://github.com/xnigthking)
- [Luiz Felipe](https://github.com/luizfelipe90)
- [Marcos](https://github.com/ml2000322)
- [Pedro Matos](https://github.com/PMDL-0310)
- [Ryan Gabriel](https://github.com/Ryan25023110)
- [Tiago Martins](https://github.com/Massacral)
- [William dos Santos Rodrigues](https://github.com/William-Willam)

### 👨‍🏫 Orientação  
- [Hudson Neves](https://github.com/HudsonNeves)

---

## 📺 Apresentação

- [Link para apresentação do projeto](https://gamma.app/docs/DF-Vistorias-zfan18gjilzvg5z)

---

# ![DF Vistorias](imagens/parte_do_projeto.gif)
