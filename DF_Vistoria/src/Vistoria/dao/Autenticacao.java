package Vistoria.dao;

import Vistoria.model.Cliente;
import Vistoria.model.Funcionario;

public class Autenticacao {
    private UsuarioDAO usuarioDAO;
    
    public Autenticacao() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public String autenticarUsuario(String identificador, String senha) {
        // Primeiro tenta autenticar como cliente (CPF)
        Cliente cliente = usuarioDAO.autenticarCliente(identificador, senha);
        if (cliente != null) {
            return "CLIENTE;" + cliente.getIdCliente() + ";" + cliente.getNome();
        }
        
        // Se não for cliente, tenta autenticar como funcionário (matrícula)
        Funcionario funcionario = usuarioDAO.autenticarFuncionario(identificador, senha);
        if (funcionario != null) {
            return funcionario.getCargo().toUpperCase() + ";" + 
                   funcionario.getIdFuncionario() + ";" + 
                   funcionario.getNome();
        }
        
        return "ERRO;Usuário não encontrado ou senha incorreta";
    }
}