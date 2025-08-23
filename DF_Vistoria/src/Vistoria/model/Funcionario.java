package Vistoria.model;

public class Funcionario {
	private int idFuncionario;
	private String nome;
	private String email;
	private String matricula;
	private String senha;
	private String cargo;

	// Construtores
	public Funcionario() {
	}

	public Funcionario(int idFuncionario, String nome, String email, String matricula, String senha, String cargo) {
		this.idFuncionario = idFuncionario;
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.senha = senha;
		this.cargo = cargo;
	}
	
	public Funcionario(String nome, String email, String matricula, String senha, String cargo) {
		this.nome = nome;
		this.email = email;
		this.matricula = matricula;
		this.senha = senha;
		this.cargo = cargo;
	}

	// Getters e Setters
	public int getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
}