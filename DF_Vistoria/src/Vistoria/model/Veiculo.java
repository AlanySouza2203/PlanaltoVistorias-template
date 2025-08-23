package Vistoria.model;

public class Veiculo {
	// Adicionado o idVeiculo
	private int idVeiculo;
	private String placa;
	private String tipo_veiculo;
	private String nome_veiculo;
	private String modelo;
	private int ano_veiculo;
	private String chassi;
	private String observacoes;
	private int idCliente;

	// Construtor vazio (necessário para DAO e frameworks)
	public Veiculo() {
	}

	// Construtor completo
	public Veiculo(int idVeiculo, String placa, String tipo_veiculo, String nome_veiculo, String modelo, int ano_veiculo,
			String chassi, String observacoes, int idCliente) {
		this.idVeiculo = idVeiculo;
		this.placa = placa;
		this.tipo_veiculo = tipo_veiculo;
		this.nome_veiculo = nome_veiculo;
		this.modelo = modelo;
		this.ano_veiculo = ano_veiculo;
		this.chassi = chassi;
		this.observacoes = observacoes;
		this.idCliente = idCliente;
	}
	
    // Construtor para cadastro (sem idVeiculo, mas com observacoes)
    // ESTE É O CONSTRUTOR QUE FALTAVA
    public Veiculo(String placa, String tipo_veiculo, String nome_veiculo, String modelo, int ano_veiculo, String chassi, String observacoes, int idCliente) {
        this.placa = placa;
        this.tipo_veiculo = tipo_veiculo;
        this.nome_veiculo = nome_veiculo;
        this.modelo = modelo;
        this.ano_veiculo = ano_veiculo;
        this.chassi = chassi;
        this.observacoes = observacoes;
        this.idCliente = idCliente;
    }

	// Seu construtor original, agora duplicado (pode ser removido se não for usado)
    public Veiculo(String placa, String tipo_veiculo, String nome_veiculo, String modelo, int ano_veiculo, String chassi, int idCliente) {
        this.placa = placa;
        this.tipo_veiculo = tipo_veiculo;
        this.nome_veiculo = nome_veiculo;
        this.modelo = modelo;
        this.ano_veiculo = ano_veiculo;
        this.chassi = chassi;
        this.idCliente = idCliente;
        this.observacoes = null;
    }

	// Getters e Setters
	public int getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(int idVeiculo) {
		this.idVeiculo = idVeiculo;
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipo_veiculo() {
		return tipo_veiculo;
	}

	public void setTipo_veiculo(String tipo_veiculo) {
		this.tipo_veiculo = tipo_veiculo;
	}

	public String getNome_veiculo() {
		return nome_veiculo;
	}

	public void setNome_veiculo(String nome_veiculo) {
		this.nome_veiculo = nome_veiculo;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAno_veiculo() {
		return ano_veiculo;
	}

	public void setAno_veiculo(int ano_veiculo) {
		this.ano_veiculo = ano_veiculo;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
}