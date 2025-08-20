package Vistoria.model;

import java.util.Scanner;

public class AreaGerente {
	
	// Funções
	public void gerenciarCadastro() {
		do {
			System.out.println("----- MENU GERENCIAS-----");
			System.out.println("1. Gerenciar funcionarios");
			System.out.println("2. Gerenciar clientes");
			System.out.println("3. Retornar");
			System.out.print("Digite sua opção: ");
			opcao3 = scanner.nextInt();
			
			switch (opcao3) {
			case 1:
				
				break;
				
			case 2:
				break;
				
			case 3:
				break;
				
			default:
				System.out.println("Opção inválida.");
				break;
			}
		} while (opcao3 != 3);
	}
	
	
	//Atributos
	Scanner scanner = new Scanner(System.in);
	int opcao, opcao2, opcao3, opcao4;
	
	//exibirMenu
	public void exibirMenu() {
		do {
            System.out.println("----- MENU DO GERENTE -----");
            System.out.println("1. Gerenciar cadastros");
            System.out.println("2. Gerenciar relatorios");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
			case 1:
				gerenciarCadastro();
				break;
			case 2:
				break;
				
			case 3:
				System.out.println("Saindo do sistema");
				break;
				
			default:
				System.out.println("Opção inválida.");
				break;
			}
		} while (opcao !=3);
		
		System.out.println("Fim do menu gerente");
	}
	

}
