package Vistoria.view;

import java.util.Scanner;
import Vistoria.dao.FuncionarioDAO;

public class MenuVistoria {
	//Atributos
	Scanner scanner = new Scanner(System.in);
	int opcao, opcao2, opcao4;
	private int idVistoriadorLogado;
	
	// Funções
	
	//exibirMenu
	public MenuVistoria(int idVistoriadorLogado) {
		this.idVistoriadorLogado = idVistoriadorLogado;
	}
	
	public void exibirMenu() {
		do {
            System.out.println("----- MENU DO GERENTE -----");
            System.out.println("1. Agendamentos");
			System.out.println("2. Realizar Vistoria");
            System.out.println("3. Relatório Diário");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            
            switch (opcao) {
			case 1: // Abrir Agendamentos com status "Aberto"
				FuncionarioDAO agendamento = new FuncionarioDAO();
				agendamento.listarAgendamentosPorStatus("Aberto");
				
				break;
			case 2: // Abrir agendamentos com status "Fechado" e realizar vistoria
				
				break;
			case 3: // Relatório Diário de vistorias realizadas pelo vistoriador
				
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
