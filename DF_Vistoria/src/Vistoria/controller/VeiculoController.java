package Vistoria.controller;

import Vistoria.dao.VeiculoDAO;
import Vistoria.model.Veiculo;
import java.util.List;

public class VeiculoController {

    private VeiculoDAO veiculoDAO;

    public VeiculoController() {
        this.veiculoDAO = new VeiculoDAO();
    }

    /**
     * Cadastra um novo veículo.
     *
     * @param veiculo O objeto Veiculo a ser salvo.
     * @return true se o cadastro foi bem-sucedido, false caso contrário.
     */
    public boolean cadastrarVeiculo(Veiculo veiculo) {
        return veiculoDAO.cadastrar(veiculo);
    }

    /**
     * Busca todos os veículos no banco de dados.
     *
     * @return Uma lista de objetos Veiculo.
     */
    public List<Veiculo> listarVeiculos() {
        return veiculoDAO.listarVeiculos();
    }
    
    // Você pode adicionar métodos para atualizar, deletar e buscar veículos.
}