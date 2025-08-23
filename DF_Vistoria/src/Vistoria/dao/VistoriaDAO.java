package Vistoria.dao;

import Vistoria.model.Vistoria;
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
    public void adicionarVistoria(Vistoria vistoria) {
        String sql = "INSERT INTO vistoria (data_vistoria, resultado, observacoes, idAgendamento, idFuncionario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, vistoria.getData_vistoria());
            stmt.setString(2, vistoria.getResultado());
            stmt.setString(3, vistoria.getObservacoes());
            stmt.setInt(4, vistoria.getIdAgendamento());
            stmt.setInt(5, vistoria.getIdFuncionario());
            
            stmt.executeUpdate();
            System.out.println("Vistoria adicionada com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao adicionar vistoria: " + e.getMessage());
        }
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
}