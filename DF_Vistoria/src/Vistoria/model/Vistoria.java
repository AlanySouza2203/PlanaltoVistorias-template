package Vistoria.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Vistoria {
	
	private int idVistoria;
	private String data_vistoria;
	private String resultado;
	private String observacoes;
	private int idAgendamento;
	private int idFuncionario;
	
	// Construtor vazio
	public Vistoria() {
	}
	
	// Construtor completo
	public Vistoria(int idVistoria, String data_vistoria, String resultado, String observacoes, int idAgendamento, int idFuncionario) {
		this.idVistoria = idVistoria;
		this.data_vistoria = data_vistoria;
		this.resultado = resultado;
		this.observacoes = observacoes;
		this.idAgendamento = idAgendamento;
		this.idFuncionario = idFuncionario;
	}
	
	// Getters e Setters
	public int getIdVistoria() {
		return idVistoria;
	}

	public void setIdVistoria(int idVistoria) {
		this.idVistoria = idVistoria;
	}

	public String getData_vistoria() {
		return data_vistoria;
	}

	public void setData_vistoria(String data_vistoria) {
		this.data_vistoria = data_vistoria;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public int getIdAgendamento() {
		return idAgendamento;
	}

	public void setIdAgendamento(int idAgendamento) {
		this.idAgendamento = idAgendamento;
	}

	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
}