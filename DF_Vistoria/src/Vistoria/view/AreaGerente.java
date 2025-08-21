package Vistoria.view;

import java.util.Scanner;
import java.util.List;
import Vistoria.dao.GerenteDAO;
import Vistoria.model.Funcionario;

public class AreaGerente {
	
	//Atributos
	Scanner scanner = new Scanner(System.in);
	GerenteDAO dao = new GerenteDAO();
	int opcao, opcao2, opcao4, opcao5;
	
	// Funções
	public void gerenciarFuncionarios() {
		do {
		System.out.println("----- MENU DE GERENCIAMENTO DE FUNCIONARIOS -----");
        System.out.println("1. Listar funcionarios");
		System.out.println("2. Excluir funcionario");
        System.out.println("3. Sair");
        System.out.print("Digite sua opção: ");
        opcao5 = scanner.nextInt();
        scanner.nextLine();
        switch(opcao5) {
			case 1: // Gerenciar Funcionarios
				List<Funcionario> funcionarios = dao.listarFuncionarios();
				System.out.println("\n--- Lista de Funcionarios ---");
				for (Funcionario f : funcionarios) {
					System.out.println(f.getIdFuncionario() + " - " + f.getNome() + " (" + f.getEmail() + ")" + " - " + f.getMatricula() + " - " + f.getCargo());
				}
				break;
			case 2: // Gerenciar Clientes
				System.out.print("Digite a matricula que deseja excluir: ");
				String matriculaExcluir = scanner.nextLine();
				dao.excluirFuncionario(matriculaExcluir);
				break;
				
			case 3:
				break;
				
			default:
				System.out.println("Saindo...");
				break;
        	}
		} while (opcao5 != 3);
	}
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
				gerenciarFuncionarios();
				break;
			case 2: // Gerenciar Clientes
				dao.excluirFuncionario("1234");
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
		} while (opcao !=4);
		
		System.out.println("Fim do menu gerente");
	}
	

}
