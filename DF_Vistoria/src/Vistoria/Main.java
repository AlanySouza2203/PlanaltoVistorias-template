package Vistoria;

import Vistoria.dao.Autenticacao;
import Vistoria.dao.ClienteDAO;
import Vistoria.model.Cliente;
import Vistoria.model.MenuCliente;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Use try-with-resources para garantir que o scanner seja fechado
        try (Scanner scanner = new Scanner(System.in)) {
            Autenticacao authService = new Autenticacao();
            ClienteDAO clienteDAO = new ClienteDAO();

            System.out.println("==========================================");
            System.out.println("       SISTEMA DE VISTORIA VEICULAR    ");
            System.out.println("==========================================");
            System.out.println("1- Já sou cadastrado (Login)");
            System.out.println("2- Não sou cadastrado (Cadastro Cliente)");
            System.out.print("Escolha uma opção: ");
            String escolha = scanner.nextLine();

            if (escolha.equals("2")) {
                System.out.println("\n=== CADASTRO DE CLIENTE ===");

                System.out.print("Nome: ");
                String nome = scanner.nextLine().trim();

                System.out.print("CPF: ");
                String cpf = scanner.nextLine().trim();

                System.out.print("Telefone: ");
                String telefone = scanner.nextLine().trim();

                System.out.print("Email: ");
                String email = scanner.nextLine().trim();

                System.out.print("Senha: ");
                String senha = scanner.nextLine().trim();

                Cliente novoCliente = new Cliente();
                novoCliente.setNome(nome);
    			novoCliente.setCpf(cpf);
                novoCliente.setTelefone(telefone);
                novoCliente.setEmail(email);
                novoCliente.setSenha(senha);

                boolean cadastrado = clienteDAO.inserir(novoCliente);

                if (cadastrado) {
                    System.out.println("\n Cliente cadastrado com sucesso!");
                    System.out.println("Agora faça o login para continuar.\n");
                } else {
                    System.out.println("\n  Erro ao cadastrar cliente. Tente novamente.\n");
                    return; // encerra o programa se não cadastrou
                }
            }

            // === LOGIN ===
            System.out.println("==========================================");
            System.out.println("                  LOGIN                   ");
            System.out.println("==========================================");

            System.out.print("Digite CPF (cliente) ou Matrícula (funcionário): ");
            String identificador = scanner.nextLine().trim();

            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine().trim();

            System.out.println("\n⏳ Processando login...\n");

            String resultado = authService.autenticarUsuario(identificador, senha);
            String[] partes = resultado.split(";");

            if (partes[0].equals("ERRO")) {
                System.out.println("❌ " + partes[1]);
            } else {
                System.out.println("==========================================");
                System.out.println("Login bem-sucedido!");
                System.out.println("Cargo: " + partes[0]);
                System.out.println("ID: " + partes[1]);
                System.out.println("Nome: " + partes[2]);
                System.out.println("==========================================");

                System.out.println("\n➡️  Redirecionando para o menu...\n");
                switch (partes[0]) {
                    case "CLIENTE":
                        // O ID do cliente é a segunda parte do resultado do login
                        int idCliente = Integer.parseInt(partes[1]); 
                        MenuCliente menuCliente = new MenuCliente(idCliente);
                        menuCliente.iniciarMenu();
                        break;
                    case "VISTORIADOR":
                        System.out.println("Menu do Vistoriador em construção...");
                        break;
                    case "GERENTE":
                        System.out.println("Menu do Gerente em construção...");
                        break;
                }
            }
        }
    }
}