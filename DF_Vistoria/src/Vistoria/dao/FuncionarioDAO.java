package Vistoria.dao;

import Vistoria.model.Agendamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Vistoria.DB.Conexao;
import Vistoria.model.Funcionario;

public class FuncionarioDAO {

    /**
     * Retorna uma lista com todos os funcionários cadastrados no banco de dados.
     * @return Uma lista de objetos Funcionario.
     */
    public List<Funcionario> listarTodos() {
        List<Funcionario> listaFuncionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setMatricula(rs.getString("matricula"));
                funcionario.setCargo(rs.getString("cargo"));
                listaFuncionarios.add(funcionario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar todos os funcionários: " + e.getMessage());
            e.printStackTrace();
        }
        return listaFuncionarios;
    }

    public List<Agendamento> listarAgendamentosPorStatus(String status) {
        List<Agendamento> listaAgendamentos = new ArrayList<>();
        String sql = "SELECT * FROM agendamento WHERE status_agendamento = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Agendamento agendamento = new Agendamento();
                    agendamento.setIdAgendamento(rs.getInt("idAgendamento"));
                    agendamento.setData_agendamento(rs.getString("data_agendamento"));
                    agendamento.setStatus_agendamento(rs.getString("status_agendamento"));
                    agendamento.setHora(rs.getString("hora"));
                    agendamento.setIdCliente(rs.getInt("idCliente"));
                    agendamento.setIdVeiculo(rs.getInt("idVeiculo"));

                    listaAgendamentos.add(agendamento);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar agendamentos: " + e.getMessage());
            e.printStackTrace();
        }
        return listaAgendamentos;
    }

    public void cadastrarFuncionario(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nome, email, matricula, senha, cargo) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getEmail());
            stmt.setString(3, funcionario.getMatricula());
            stmt.setString(4, funcionario.getSenha());
            stmt.setString(5, funcionario.getCargo());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar funcionário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Funcionario login(String matricula, String senha) {
        String sql = "SELECT * FROM funcionario WHERE matricula = ? AND senha = ?";
        Funcionario funcionario = null;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, matricula);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    funcionario = new Funcionario();
                    funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                    funcionario.setNome(rs.getString("nome"));
                    funcionario.setEmail(rs.getString("email"));
                    funcionario.setMatricula(rs.getString("matricula"));
                    funcionario.setCargo(rs.getString("cargo"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao tentar login do funcionário: " + e.getMessage());
            e.printStackTrace();
        }
        return funcionario;
    }
}