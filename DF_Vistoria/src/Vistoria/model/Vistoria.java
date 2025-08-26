package Vistoria.model;

import java.time.LocalDate;

/**
 * A classe Vistoria representa uma vistoria realizada, vinculada a um agendamento e um funcionário.
 */
public class Vistoria {
    private int idVistoria;
    private String data_vistoria;
    private String resultado;
    private String status_pagamento;
    private String observacoes;
    private int idAgendamento;
    private int idFuncionario;
    private Agendamento agendamento;
    private Funcionario funcionario;

    public Vistoria() {
    }
    //Construtor com ids de agendamento e funcionario
    public Vistoria(int idVistoria, String data_vistoria, String resultado, String status_pagamento, String observacoes, int idAgendamento,
			int idFuncionario) {
		super();
		this.idVistoria = idVistoria;
		this.data_vistoria = data_vistoria;
		this.resultado = resultado;
		this.status_pagamento = status_pagamento;
		this.observacoes = observacoes;
		this.idAgendamento = idAgendamento;
		this.idFuncionario = idFuncionario;
	}
    
    
    public Vistoria(int idVistoria, String data_vistoria, String resultado, String status_pagamento, String observacoes, Agendamento agendamento, Funcionario funcionario) {
        this.idVistoria = idVistoria;
        this.data_vistoria = data_vistoria;
        this.resultado = resultado;
        this.status_pagamento = status_pagamento;
        this.observacoes = observacoes;
        this.agendamento = agendamento;
        this.funcionario = funcionario;
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
    
    public String getStatus_pagamento() {
        return status_pagamento;
    }

    public void setStatus_pagamento(String status_pagamento) {
        this.status_pagamento = status_pagamento;
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
	public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }
}
