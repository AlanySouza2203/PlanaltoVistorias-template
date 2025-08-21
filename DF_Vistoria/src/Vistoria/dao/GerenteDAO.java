package Vistoria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Vistoria.DB.Conexao;
import Vistoria.model.Funcionario;

public class GerenteDAO {
	
    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT idFuncionario, nome, email, matricula, senha, cargo FROM funcionario";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setMatricula(rs.getString("matricula"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionarios.add(funcionario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return funcionarios;
    }
	
	public void excluirFuncionario(String matricula) {
        String sql = "DELETE FROM funcionario WHERE matricula = ?";
        
        try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, matricula);
            int rowsAffected = stmt.executeUpdate();
            
            // Retorna se a exclusão foi bem-sucedida
            if (rowsAffected > 0) {
                System.out.println("Funcionario removido com sucesso.");
            } else {
                System.out.println("Nenhum funcionario com matricula " + matricula + " foi encontrado para remoção.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
