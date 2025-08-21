package Vistoria.view;

import java.util.Scanner;

public class AreaGerente {
	
	//Atributos
	Scanner scanner = new Scanner(System.in);
	int opcao, opcao2, opcao4;
	
	// Funções
	
	//exibirMenu
	public void exibirMenu() {
		do {
            System.out.println("----- MENU DO GERENTE -----");
            System.out.println("1. Gerenciar funcionarios");
			System.out.println("2. Gerenciar clientes");
            System.out.println("3. Gerenciar relatorios");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
			case 1: // Gerenciar Funcionarios
				break;
			case 2: // Gerenciar Clientes
				break;
			case 3: // Gerenciar Relatórios
				break;
			case 4: // Sair do menu
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
