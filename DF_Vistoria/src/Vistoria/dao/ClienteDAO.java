package Vistoria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Vistoria.DB.Conexao;
import Vistoria.model.Cliente;

public class ClienteDAO {

    /**
     * Insere um novo cliente no banco de dados.
     * @param cliente O objeto Cliente a ser inserido.
     * @return true se a inserção for bem-sucedida, false caso contrário.
     */
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, email, senha) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getTelefone());
            stmt.setString(4, cliente.getEmail());
            stmt.setString(5, cliente.getSenha());
            
            int rowsAffected = stmt.executeUpdate();
            
            // Retorna true se pelo menos uma linha foi afetada (inserida)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Retorna false em caso de erro no banco de dados
            return false;
        }
    }

    /**
     * Retorna uma lista de todos os clientes no banco de dados.
     * @return Uma lista de objetos Cliente.
     */
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT idCliente, nome, cpf, telefone, email, senha FROM cliente";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    /**
     * Atualiza os dados de um cliente existente com base no CPF.
     * @param cliente O objeto Cliente com os dados a serem atualizados.
     * @return true se a atualização for bem-sucedida, false caso contrário.
     */
    public boolean modificar(Cliente cliente) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ?, email = ?, senha = ? WHERE cpf = ?";
        
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getSenha());
            stmt.setString(5, cliente.getCpf());
            
            int rowsAffected = stmt.executeUpdate();
            
            // Retorna true se a atualização foi bem-sucedida
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deleta um cliente do banco de dados com base no CPF.
     * @param cpf O CPF do cliente a ser excluído.
     * @return true se a exclusão for bem-sucedida, false caso contrário.
     */
    public boolean excluir(String cpf) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";
        
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            int rowsAffected = stmt.executeUpdate();
            
            // Retorna true se a exclusão foi bem-sucedida
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Cliente login(String cpf, String senha) {
        String sql = "SELECT * FROM cliente WHERE cpf = ? AND senha = ?";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));
                cliente.setSenha(rs.getString("senha"));
                return cliente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // não encontrou
    }
}