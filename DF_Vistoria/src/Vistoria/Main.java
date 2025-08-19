package Vistoria;

import Vistoria.dao.Autenticacao;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Autenticacao authService = new Autenticacao();
        
        System.out.println("=== SISTEMA DE VISTORIA ===");
        System.out.println("===        LOGIN        ===");
        
        System.out.print("Digite CPF (cliente) ou Matrícula (funcionário): ");
        String identificador = scanner.nextLine().trim(); // o .trim() é usado para remover espaços adicionados acidentalmente
        
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine().trim();
        
        System.out.println("\n=== PROCESSANDO LOGIN ===");
        
        String resultado = authService.autenticarUsuario(identificador, senha);
        String[] partes = resultado.split(";");
        
        if (partes[0].equals("ERRO")) {
            System.out.println("❌ " + partes[1]);
        } else {
            System.out.println("Login bem-sucedido!");
            System.out.println("Cargo: " + partes[0]);
            System.out.println("ID: " + partes[1]);
            System.out.println("Nome: " + partes[2]);
            
            // Simulação de redirecionamento
            System.out.println("\n=== REDIRECIONANDO ===");
            switch (partes[0]) {
                case "CLIENTE":
                    System.out.println("Direciona ao perfil do cliente");
                    // podemos criar um método que exibisse todo as opções do menu do cliente
                    break;
                case "VISTORIADOR":
                    System.out.println("Direciona ao perfil do vistoriador");
                    // podemos criar um método que exibisse todo as opções do menu do vistoriador
                    break;
                case "GERENTE":
                    System.out.println("Direciona ao perfil do Gerente");
                    // podemos criar um método que exibisse todo as opções do menu do gerente
                    break;
            }
        }
        
        scanner.close();
    }
}