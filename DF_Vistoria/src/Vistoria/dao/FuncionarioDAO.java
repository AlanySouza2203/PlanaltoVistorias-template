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

    public List<Agendamento> listarAgendamentosPorStatus(String status) {
        List<Agendamento> listaAgendamentos = new ArrayList<>();
        String sql = "SELECT * FROM agendamento WHERE status_agendamento = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Define o valor do status no placeholder
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
}