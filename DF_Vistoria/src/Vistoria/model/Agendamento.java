package Vistoria.model;

public class Agendamento {
	private int idAgendamento;
	private String data_agendamento;
	private String status_agendamento;
	private String hora;
	private int idCliente;
	private int idVeiculo;
	
	// Construtor vazio (necessário para DAO e frameworks)
	public Agendamento() {
	}
	
	// Construtor completo
	// Adicionado idVeiculo ao construtor completo, conforme boa prática,
	// embora a inserção no banco seja automática.
	public Agendamento(int idAgendamento, String data_agendamento, String status_agendamento, String hora, int idCliente,
			int idVeiculo) {
		this.idAgendamento = idAgendamento;
		this.data_agendamento = data_agendamento;
		this.status_agendamento = status_agendamento;
		this.hora = hora;
		this.idCliente = idCliente;
		this.idVeiculo = idVeiculo;
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
	
}
