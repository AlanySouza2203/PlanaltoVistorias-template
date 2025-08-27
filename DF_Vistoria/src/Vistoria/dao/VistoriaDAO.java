package Vistoria.dao;

import Vistoria.model.Vistoria;
import Vistoria.model.Cliente;
import Vistoria.model.Veiculo;
import Vistoria.model.Agendamento;
import Vistoria.model.Funcionario;
import Vistoria.DB.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VistoriaDAO {

    /**
     * Adiciona uma nova vistoria ao banco de dados.
     * @param vistoria O objeto Vistoria a ser adicionado.
     */
	public boolean inserirVistoria(Vistoria vistoria) {
        String sql = "INSERT INTO vistoria (data_vistoria, resultado, status_pagamento, observacoes, idAgendamento, idFuncionario) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vistoria.getData_vistoria());
            stmt.setString(2, vistoria.getResultado());
            stmt.setString(3, vistoria.getStatus_pagamento());
            stmt.setString(4, vistoria.getObservacoes());
            stmt.setInt(5, vistoria.getAgendamento().getIdAgendamento());
            stmt.setInt(6, vistoria.getFuncionario().getIdFuncionario());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir vistoria: " + e.getMessage());
            return false;
        }
    }

	/**
     * Lista todas as vistorias de um funcionário específico.
     * @param idFuncionario O ID do funcionário para o qual o relatório é gerado.
     * @return Uma lista de objetos Vistoria.
     */
    public List<Vistoria> listarVistoriasPorFuncionario(int idFuncionario) {
        List<Vistoria> vistorias = new ArrayList<>();
        String sql = "SELECT v.idVistoria, v.data_vistoria, v.resultado, v.observacoes, "
                + "c.nome AS nome_cliente, c.cpf, c.email, c.telefone, "
                + "ve.nome_veiculo, ve.placa "
                + "FROM vistoria v "
                + "INNER JOIN agendamento a ON v.idAgendamento = a.idAgendamento "
                + "INNER JOIN cliente c ON a.idCliente = c.idCliente "
                + "INNER JOIN veiculo ve ON a.idVeiculo = ve.idVeiculo "
                + "WHERE v.idFuncionario = ?";

        try (Connection conn = Conexao.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFuncionario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vistoria vistoria = new Vistoria();
                vistoria.setIdVistoria(rs.getInt("idVistoria"));
                vistoria.setData_vistoria(rs.getString("data_vistoria"));
                vistoria.setResultado(rs.getString("resultado"));
                vistoria.setObservacoes(rs.getString("observacoes"));

                // Cria e popula os objetos aninhados para o relatório
                Cliente cliente = new Cliente();
                cliente.setNome(rs.getString("nome_cliente"));

                Veiculo veiculo = new Veiculo();
                veiculo.setNome_veiculo(rs.getString("nome_veiculo"));
                veiculo.setPlaca(rs.getString("placa"));

                Agendamento agendamento = new Agendamento();
                agendamento.setCliente(cliente);
                agendamento.setVeiculo(veiculo);
                vistoria.setAgendamento(agendamento);

                vistorias.add(vistoria);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vistorias por funcionário: " + e.getMessage());
        }
        return vistorias;
    }
    
    
    public List<Vistoria> listarVistoriasPorCliente(int idCliente) {
        List<Vistoria> vistorias = new ArrayList<>();
        String sql = "SELECT v.idVistoria, v.data_vistoria, v.resultado, v.status_pagamento, v.observacoes, "
                + "a.idAgendamento, a.data_agendamento, a.hora, "
                + "ve.placa, ve.nome_veiculo, ve.modelo, "
                + "f.nome AS nome_funcionario "
                + "FROM vistoria v "
                + "INNER JOIN agendamento a ON v.idAgendamento = a.idAgendamento "
                + "INNER JOIN veiculo ve ON a.idVeiculo = ve.idVeiculo "
                + "INNER JOIN funcionario f ON v.idFuncionario = f.idFuncionario "
                + "WHERE a.idCliente = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vistoria vistoria = new Vistoria();
                vistoria.setIdVistoria(rs.getInt("idVistoria"));
                vistoria.setData_vistoria(rs.getString("data_vistoria"));
                vistoria.setResultado(rs.getString("resultado"));
                vistoria.setStatus_pagamento(rs.getString("status_pagamento"));
                vistoria.setObservacoes(rs.getString("observacoes"));

                // Cria e popula objetos relacionados
                Agendamento agendamento = new Agendamento();
                agendamento.setIdAgendamento(rs.getInt("idAgendamento"));
                agendamento.setData_agendamento(rs.getString("data_agendamento"));
                agendamento.setHora(rs.getString("hora"));

                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setNome_veiculo(rs.getString("nome_veiculo"));
                veiculo.setModelo(rs.getString("modelo"));
                agendamento.setVeiculo(veiculo);

                Funcionario funcionario = new Funcionario();
                funcionario.setNome(rs.getString("nome_funcionario"));
                
                vistoria.setAgendamento(agendamento);
                vistoria.setFuncionario(funcionario);

                vistorias.add(vistoria);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar vistorias por cliente: " + e.getMessage());
        }
        return vistorias;
    }
	
    /**
     * Retorna uma lista de todas as vistorias do banco de dados.
     * @return Uma lista de objetos Vistoria.
     */
    public List<Vistoria> listarVistorias() {
        List<Vistoria> vistorias = new ArrayList<>();
        String sql = "SELECT * FROM vistoria";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Vistoria vistoria = new Vistoria();
                vistoria.setIdVistoria(rs.getInt("idVistoria"));
                vistoria.setData_vistoria(rs.getString("data_vistoria"));
                vistoria.setResultado(rs.getString("resultado"));
                vistoria.setObservacoes(rs.getString("observacoes"));
                vistoria.setIdAgendamento(rs.getInt("idAgendamento"));
                vistoria.setIdFuncionario(rs.getInt("idFuncionario"));
                vistorias.add(vistoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar vistorias: " + e.getMessage());
        }
        return vistorias;
    }

    /**
     * Busca uma vistoria pelo seu ID.
     * @param id O ID da vistoria.
     * @return O objeto Vistoria correspondente ou null se não for encontrado.
     */
    public Vistoria buscarVistoriaPorId(int id) {
        Vistoria vistoria = null;
        String sql = "SELECT * FROM vistoria WHERE idVistoria = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    vistoria = new Vistoria();
                    vistoria.setIdVistoria(rs.getInt("idVistoria"));
                    vistoria.setData_vistoria(rs.getString("data_vistoria"));
                    vistoria.setResultado(rs.getString("resultado"));
                    vistoria.setObservacoes(rs.getString("observacoes"));
                    vistoria.setIdAgendamento(rs.getInt("idAgendamento"));
                    vistoria.setIdFuncionario(rs.getInt("idFuncionario"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar vistoria por ID: " + e.getMessage());
        }
        return vistoria;
    }

    /**
     * Atualiza uma vistoria existente no banco de dados.
     * @param vistoria O objeto Vistoria com os dados atualizados.
     */
    public void atualizarVistoria(Vistoria vistoria) {
        String sql = "UPDATE vistoria SET data_vistoria = ?, resultado = ?, observacoes = ?, idAgendamento = ?, idFuncionario = ? WHERE idVistoria = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vistoria.getData_vistoria());
            stmt.setString(2, vistoria.getResultado());
            stmt.setString(3, vistoria.getObservacoes());
            stmt.setInt(4, vistoria.getIdAgendamento());
            stmt.setInt(5, vistoria.getIdFuncionario());
            stmt.setInt(6, vistoria.getIdVistoria());
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Vistoria atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma vistoria encontrada com o ID " + vistoria.getIdVistoria());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao atualizar vistoria: " + e.getMessage());
        }
    }

    /**
     * Deleta uma vistoria do banco de dados pelo seu ID.
     * @param id O ID da vistoria a ser deletada.
     */
    public void deletarVistoria(int id) {
        String sql = "DELETE FROM vistoria WHERE idVistoria = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Vistoria deletada com sucesso!");
            } else {
                System.out.println("Nenhuma vistoria encontrada com o ID " + id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao deletar vistoria: " + e.getMessage());
        }
    }
    
    // atualizar o status do pagamento para a DashboardCliente
    public boolean atualizarStatusPagamento(int idVistoria, String status) {
        String sql = "UPDATE vistoria SET status_pagamento = ? WHERE idVistoria = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            stmt.setInt(2, idVistoria);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status de pagamento: " + e.getMessage());
            return false;
        }
    }
}