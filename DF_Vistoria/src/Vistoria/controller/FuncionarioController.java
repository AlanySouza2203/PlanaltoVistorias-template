package Vistoria.controller;

import Vistoria.model.Funcionario;
import Vistoria.dao.FuncionarioDAO;
import java.util.List;

public class FuncionarioController {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioController() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    /**
     * Valida e cadastra um novo funcionário no sistema.
     * @param funcionario O objeto Funcionario a ser cadastrado.
     * @return true se o cadastro for bem-sucedido, false caso contrário.
     */
    public boolean cadastrarFuncionario(Funcionario funcionario) {
        // Validação de dados de forma simples
        if (funcionario.getNome().isEmpty() || funcionario.getMatricula().isEmpty() || funcionario.getSenha().isEmpty()) {
            return false; // Retorna false se os dados forem inválidos
        }
        
        // Chama o DAO para salvar no banco de dados
        funcionarioDAO.cadastrarFuncionario(funcionario);
        return true;
    }
    
    /**
     * Obtém uma lista de todos os funcionários cadastrados.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarFuncionarios() {
        return funcionarioDAO.listarTodos();
    }
    
    // Futuros métodos como excluirFuncionario(), atualizarFuncionario(), etc.
    // seriam implementados aqui.
}