package Vistoria.dao;

import Vistoria.model.Agendamento;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    private Connection conn;

    public AgendamentoDAO(Connection conn) {
        this.conn = conn;
    }

    // Criar (INSERT)
    public void inserir(Agendamento agendamento) throws SQLException {
        String sql = "INSERT INTO agendamento (data_agendamento, status_agendamento, hora, idCliente, idVeiculo) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agendamento.getData_agendamento());
            stmt.setString(2, agendamento.getStatus_agendamento());
            stmt.setString(3, agendamento.getHora());
            stmt.setInt(4, agendamento.getIdCliente());
            stmt.setInt(5, agendamento.getIdVeiculo());
            stmt.executeUpdate();
        }
    }

    // Ler todos (SELECT)
    public List<Agendamento> listarTodos() throws SQLException {
        List<Agendamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM agendamento";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Agendamento ag = new Agendamento();
                ag.setIdAgendamento(rs.getInt("idAgendamento"));
                ag.setData_agendamento(rs.getString("data_agendamento"));
                ag.setStatus_agendamento(rs.getString("status_agendamento"));
                ag.setHora(rs.getString("hora"));
                ag.setIdCliente(rs.getInt("idCliente"));
                ag.setIdVeiculo(rs.getInt("idVeiculo"));
                lista.add(ag);
            }
        }
        return lista;
    }

    // Buscar por ID
    public Agendamento buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM agendamento WHERE idAgendamento = ?";
        Agendamento ag = null;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    ag = new Agendamento();
                    ag.setIdAgendamento(rs.getInt("idAgendamento"));
                    ag.setData_agendamento(rs.getString("data_agendamento"));
                    ag.setStatus_agendamento(rs.getString("status_agendamento"));
                    ag.setHora(rs.getString("hora"));
                    ag.setIdCliente(rs.getInt("idCliente"));
                    ag.setIdVeiculo(rs.getInt("idVeiculo"));
                }
            }
        }
        return ag;
    }

    // Atualizar
    public void atualizar(Agendamento agendamento) throws SQLException {
        String sql = "UPDATE agendamento SET data_agendamento=?, status_agendamento=?, hora=?, idCliente=?, idVeiculo=? "
                   + "WHERE idAgendamento=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, agendamento.getData_agendamento());
            stmt.setString(2, agendamento.getStatus_agendamento());
            stmt.setString(3, agendamento.getHora());
            stmt.setInt(4, agendamento.getIdCliente());
            stmt.setInt(5, agendamento.getIdVeiculo());
            stmt.setInt(6, agendamento.getIdAgendamento());
            stmt.executeUpdate();
        }
    }

    // Deletar
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM agendamento WHERE idAgendamento=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Extra: Confirmar agendamento (muda status para "Realizado")
    public void confirmar(int id) throws SQLException {
        String sql = "UPDATE agendamento SET status_agendamento='Realizado' WHERE idAgendamento=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
