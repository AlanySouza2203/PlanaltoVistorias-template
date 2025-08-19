package Vistoria.dao;

// import do pacote Vistoria.DB e o java.sql
import Vistoria.DB.Conexao;
import Vistoria.model.Cliente;
import Vistoria.model.Funcionario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
	// Glauber, julio , cesar, kaua = aqui neste arquivo vai ficar a autenticação para todos usuario(vistoriador, gerente e cliente)
	
	// autenticar funcionario
	//--------------------------------------------------------------------------------------------
	// Autenticar funcionário por matrícula
    public Funcionario autenticarFuncionario(String matricula, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Conexao.getConnection();
            String sql = "SELECT * FROM funcionario WHERE matricula = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, matricula);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setMatricula(rs.getString("matricula"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setCargo(rs.getString("cargo"));
                return funcionario;
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao autenticar funcionário: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }
        return null;
    }
    //--------------------------------------------------------------------------------------------
    // Método auxiliar para fechar recursos
    private void fecharRecursos(Connection conn, PreparedStatement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }
    //--------------------------------------------------------------------------------------------
	// autenticar cliente
    
    public Cliente autenticarCliente(String cpf, String senha) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Conexao.getConnection();
            String sql = "SELECT * FROM cliente WHERE cpf = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.setString(2, senha);
            rs = stmt.executeQuery();
            
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
            System.out.println("Erro ao autenticar cliente: " + e.getMessage());
        } finally {
            fecharRecursos(conn, stmt, rs);
        }
        return null;
    }
  //--------------------------------------------------------------------------------------------
}
