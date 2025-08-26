package Vistoria.dao;

import Vistoria.model.Agendamento;
import Vistoria.model.Cliente;
import Vistoria.model.Veiculo;
import Vistoria.DB.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgendamentoDAO {

    public void adicionarAgendamento(Agendamento agendamento) {
        String sql = "INSERT INTO agendamento (data_agendamento, status_agendamento, hora, idCliente, idVeiculo) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, agendamento.getData_agendamento());
            stmt.setString(2, agendamento.getStatus_agendamento());
            stmt.setString(3, agendamento.getHora());
            stmt.setInt(4, agendamento.getIdCliente());
            stmt.setInt(5, agendamento.getIdVeiculo());
            stmt.executeUpdate();
            System.out.println("Agendamento adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao adicionar agendamento: " + e.getMessage());
        }
    }

    public List<Agendamento> listarAgendamentos() {
    	List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.idAgendamento, a.data_agendamento, a.hora, a.status_agendamento, " +
                     "c.idCliente, c.nome AS nomeCliente, " +
                     "v.idVeiculo, v.nome_veiculo AS nomeVeiculo " +
                     "FROM agendamento a " +
                     "JOIN cliente c ON a.idCliente = c.idCliente " +
                     "JOIN veiculo v ON a.idVeiculo = v.idVeiculo";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Cria os objetos Cliente e Veiculo
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nomeCliente"));

                Veiculo veiculo = new Veiculo();
                veiculo.setIdVeiculo(rs.getInt("idVeiculo"));
                veiculo.setNome_veiculo(rs.getString("nomeVeiculo"));

                // Cria Agendamento e define os objetos
                Agendamento agendamento = new Agendamento();
                agendamento.setIdAgendamento(rs.getInt("idAgendamento"));
                agendamento.setData_agendamento(rs.getString("data_agendamento"));
                agendamento.setHora(rs.getString("hora"));
                agendamento.setStatus_agendamento(rs.getString("status_agendamento"));
                agendamento.setCliente(cliente);
                agendamento.setVeiculo(veiculo);

                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao listar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }

    public Agendamento buscarAgendamentoPorId(int id) {
        Agendamento agendamento = null;
        String sql = "SELECT * FROM agendamento WHERE idAgendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    agendamento = new Agendamento();
                    agendamento.setIdAgendamento(rs.getInt("idAgendamento"));
                    agendamento.setData_agendamento(rs.getString("data_agendamento"));
                    agendamento.setStatus_agendamento(rs.getString("status_agendamento"));
                    agendamento.setHora(rs.getString("hora"));
                    agendamento.setIdCliente(rs.getInt("idCliente"));
                    agendamento.setIdVeiculo(rs.getInt("idVeiculo"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar agendamento por ID: " + e.getMessage());
        }
        return agendamento;
    }

    public void atualizarAgendamento(Agendamento agendamento) {
        String sql = "UPDATE agendamento SET data_agendamento = ?, status_agendamento = ?, hora = ?, idCliente = ?, idVeiculo = ? WHERE idAgendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, agendamento.getData_agendamento());
            stmt.setString(2, agendamento.getStatus_agendamento());
            stmt.setString(3, agendamento.getHora());
            stmt.setInt(4, agendamento.getIdCliente());
            stmt.setInt(5, agendamento.getIdVeiculo());
            stmt.setInt(6, agendamento.getIdAgendamento());
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Agendamento atualizado com sucesso!");
            } else {
                System.out.println("Nenhum agendamento encontrado com o ID " + agendamento.getIdAgendamento());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao atualizar agendamento: " + e.getMessage());
        }
    }

    public void deletarAgendamento(int id) {
        String sql = "DELETE FROM agendamento WHERE idAgendamento = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Agendamento deletado com sucesso!");
            } else {
                System.out.println("Nenhum agendamento encontrado com o ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao deletar agendamento: " + e.getMessage());
        }
    }
    
    /**
     * Conta a quantidade de agendamentos de um cliente.
     */
    public int contarAgendamentosPorCliente(int idCliente) {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE idCliente = ?";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar agendamentos: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Conta a quantidade de laudos (agendamentos com status 'Concluído').
     */
    public int contarLaudosConcluidosPorCliente(int idCliente) {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE idCliente = ? AND status_agendamento = 'Concluído'";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idCliente);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar laudos: " + e.getMessage());
        }
        return 0;
    }
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    //Inicio dos agendamentos(Funções para o DashboardVistoriador)
    //--------------------------------------------------------------------------------------------------------------------------------------------------------
    /**
     * Conta a quantidade de agendamentos por status.
     */
    public int contarAgendamentosAgendado() {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE status_agendamento = 'Pendente'";
        
        int count = 0;
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar agendamentos: " + e.getMessage());
        }
        
        return count;
    }
    public int contarAgendamentosConcluido() {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE status_agendamento = 'Concluido'";
        
        int count = 0;
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar agendamentos: " + e.getMessage());
        }
        
        return count;
    }
    public int contarAgendamentosCancelado() {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE status_agendamento = 'Cancelado'";
        
        int count = 0;
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar agendamentos: " + e.getMessage());
        }
        
        return count;
    }
    public int contarAgendamentosReservado() {
        String sql = "SELECT COUNT(*) FROM agendamento WHERE status_agendamento = 'Reservado'";
        
        int count = 0;
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao contar agendamentos: " + e.getMessage());
        }
        
        return count;
    }
    // lista agendamentos com status 'Agendado'
    public List<Agendamento> listarAgendamentosAgendadosComDetalhes() {
        List<Agendamento> agendamentos = new ArrayList<>();
        String sql = "SELECT a.idAgendamento, a.data_agendamento, a.hora, c.idCliente, c.nome, v.idVeiculo, v.placa " +
                     "FROM agendamento a " +
                     "JOIN cliente c ON a.idCliente = c.idCliente " +
                     "JOIN veiculo v ON a.idVeiculo = v.idVeiculo " +
                     "WHERE a.status_agendamento = 'Pendente'";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Agendamento agendamento = new Agendamento();
                agendamento.setIdAgendamento(rs.getInt("idAgendamento"));
                agendamento.setData_agendamento(rs.getString("data_agendamento")); // Corrigido para String
                agendamento.setHora(rs.getString("hora")); // Corrigido para String
                
                // Cria e popula objetos de Cliente e Veiculo
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));

                Veiculo veiculo = new Veiculo();
                veiculo.setIdVeiculo(rs.getInt("idVeiculo"));
                veiculo.setPlaca(rs.getString("placa"));
                
                // Associa os objetos ao agendamento
                agendamento.setCliente(cliente);
                agendamento.setVeiculo(veiculo);
                
                agendamentos.add(agendamento);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar agendamentos: " + e.getMessage());
        }
        return agendamentos;
    }
    
 // Método para atualizar o status de um agendamento
    public boolean atualizarStatusAgendamento(int idAgendamento, String novoStatus) {
        String sql = "UPDATE agendamento SET status_agendamento = ? WHERE idAgendamento = ?";
        try (Connection conn = Conexao.getConnection();
        	PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, idAgendamento);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status do agendamento: " + e.getMessage());
            return false;
        }
    }
}
