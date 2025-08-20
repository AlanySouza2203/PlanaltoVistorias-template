package Vistoria.model;

import Vistoria.dao.VeiculoDAO;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuCliente {
	
    private int idClienteLogado;

    public MenuCliente(int idClienteLogado) {
        this.idClienteLogado = idClienteLogado;
    }

    public void iniciarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenuPrincipal();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        menuLaudoTecnico(scanner);
                        break;
                    case 2:
                        menuAgendamento(scanner);
                        break;
                    case 3:
                        menuBoletos(scanner);
                        break;
                    case 4:
                        menuCadastrarVeiculo(scanner);
                        break;
                    case 0:
                        System.out.println("Fechando Menu.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
        } while (opcao != 0);

        scanner.close();
    }

    // Este método pode permanecer estático pois não depende da instância
    public static void exibirMenuPrincipal() {
        System.out.println("----- MENU CLIENTE -----");
        System.out.println("1. Laudo Técnico");
        System.out.println("2. Realizar Agendamento");
        System.out.println("3. Acessar Boletos");
        System.out.println("4. Cadastrar o meu Veiculo");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Mudei de 'public static' para 'public'
    public void menuLaudoTecnico(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n----- LAUDO TÉCNICO -----");
            System.out.println("1. Acessar Histórico de Laudos Técnicos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Acessando histórico de laudos...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // Mudei de 'public static' para 'public'
    public void menuAgendamento(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n----- REALIZAR AGENDAMENTO -----");
            System.out.println("1. Selecionar Data/Horário");
            System.out.println("2. Selecionar Tipo de Vistoria/Funcionário");
            System.out.println("3. Emissão de Boleto");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Abrindo seletor de data e horário...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 2:
                    System.out.println("Abrindo seletor de tipo de vistoria e funcionário...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 3:
                    System.out.println("Emitindo boleto...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // Mudei de 'public static' para 'public'
    public void menuBoletos(Scanner scanner) {
        int opcao;
        do {
            System.out.println("\n----- ACESSAR BOLETOS -----");
            System.out.println("1. Realizar Pagamentos");
            System.out.println("2. Acessar Histórico de Pagamentos");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Acessando tela de pagamentos...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 2:
                    System.out.println("Acessando histórico de pagamentos...");
                    // Lógica que pode usar this.idClienteLogado
                    break;
                case 0:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }

    // Removi o 'static'
    public void menuCadastrarVeiculo(Scanner scanner) {
        VeiculoDAO veiculoDAO = new VeiculoDAO();

        System.out.println("\n----- CADASTRAR VEÍCULO -----");

        System.out.print("Placa do veículo: ");
        String placa = scanner.nextLine().trim();

        System.out.print("Tipo do veículo (ex: Carro, Moto): ");
        String tipoVeiculo = scanner.nextLine().trim();

        System.out.print("Modelo do veículo: ");
        String modelo = scanner.nextLine().trim();

        System.out.print("Ano do veículo: ");
        int anoVeiculo = 0;
        try {
            anoVeiculo = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Ano inválido. Por favor, digite um número.");
            scanner.nextLine();
            return;
        }

        System.out.print("Número do Chassi: ");
        String chassi = scanner.nextLine().trim();

        Veiculo novoVeiculo = new Veiculo(placa, tipoVeiculo, modelo, anoVeiculo, chassi, this.idClienteLogado);

        boolean cadastrado = veiculoDAO.cadastrar(novoVeiculo);
        if (cadastrado) {
            System.out.println("Veículo cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar veículo. Verifique se a placa já existe.");
        }
    }
}