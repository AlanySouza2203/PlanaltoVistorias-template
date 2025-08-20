package Vistoria.model;

public class Veiculo {
	private String placa;
	private String tipo_veiculo;
	private String modelo;
	private int ano_veiculo;
	private String chassi;
	private int idCliente;

	// Construtor vazio (necessário para DAO e frameworks)
	public Veiculo() {
	}

	// Construtor completo
	public Veiculo(String placa, String tipo_veiculo, String modelo, int ano_veiculo, String chassi, int idCliente) {
		this.placa = placa;
		this.tipo_veiculo = tipo_veiculo;
		this.modelo = modelo;
		this.ano_veiculo = ano_veiculo;
		this.chassi = chassi;
		this.idCliente = idCliente;
	}

	// Getters e Setters
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

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
}
