package Vistoria.model;

import java.util.Date;

public class Agendamento {
	private int idAgendamento;
	private String data_agendamento;
	private String status_agendamento;
	private String hora;
	private Cliente cliente; // Adicionado objeto Cliente
	private Veiculo veiculo; // Adicionado objeto Veiculo
	private int idCliente;
	private int idVeiculo;
	
	// Construtor vazio (necessário para DAO e frameworks)
	public Agendamento() {
	}
	
	
	// Construtor Completo
	public Agendamento(int idAgendamento, String data_agendamento, String status_agendamento, String hora,
			int idCliente, int idVeiculo) {
		super();
		this.idAgendamento = idAgendamento;
		this.data_agendamento = data_agendamento;
		this.status_agendamento = status_agendamento;
		this.hora = hora;
		this.idCliente = idCliente;
		this.idVeiculo = idVeiculo;
	}

	// Construtor para tabela em DashboardVistoriador
	public Agendamento(int idAgendamento, String data_agendamento, String status_agendamento, String hora, Cliente cliente,
			Veiculo veiculo) {
		this.idAgendamento = idAgendamento;
		this.data_agendamento = data_agendamento;
		this.status_agendamento = status_agendamento;
		this.hora = hora;
		this.cliente = cliente;
		this.veiculo = veiculo;
	}
	
	//Getters e Setters
	public int getIdAgendamento() {
		return idAgendamento;
	}
	public void setIdAgendamento(int idAgendamento) {
		this.idAgendamento = idAgendamento;
	}
	public String getData_agendamento() {
		return data_agendamento;
	}
	public void setData_agendamento(String data_agendamento) {
		this.data_agendamento = data_agendamento;
	}
	public String getStatus_agendamento() {
		return status_agendamento;
	}
	public void setStatus_agendamento(String status_agendamento) {
		this.status_agendamento = status_agendamento;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}


	public int getIdVeiculo() {
		return idVeiculo;
	}


	public void setIdVeiculo(int idVeiculo) {
		this.idVeiculo = idVeiculo;
	}


	// Novos Getters e Setters para Cliente e Veiculo
	// Necessário para tabela de agendamentos em DashboardVistoriador
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Veiculo getVeiculo() {
		return veiculo;
	}
	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
	
}