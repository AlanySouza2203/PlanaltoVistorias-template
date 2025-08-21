package Vistoria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Vistoria.DB.Conexao;
import Vistoria.model.Veiculo;

public class VeiculoDAO {

	// Cadastrar veículo
	// O INSERT não precisa de idVeiculo porque ele é AUTO_INCREMENT.
	// O SQL já está correto, pois o construtor do Veiculo agora passa a observacao.
	public boolean  cadastrar(Veiculo veiculo) {
		String sql = "INSERT INTO veiculo (placa, tipo_veiculo, nome_veiculo, modelo, ano_veiculo, chassi, observacoes, idCliente) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getTipo_veiculo());
			stmt.setString(3, veiculo.getNome_veiculo());
			stmt.setString(4, veiculo.getModelo());
			stmt.setInt(5, veiculo.getAno_veiculo());
			stmt.setString(6, veiculo.getChassi());
			stmt.setString(7, veiculo.getObservacoes());
			stmt.setInt(8, veiculo.getIdCliente());

			int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Listar todos os veículos
	// Alterado o SELECT para incluir 'idVeiculo' e o ResultSet para ler essa coluna.
	public List<Veiculo> listarVeiculos() {
		List<Veiculo> lista = new ArrayList<>();
		String sql = "SELECT idVeiculo, placa, tipo_veiculo, nome_veiculo, modelo, ano_veiculo, chassi, observacoes, idCliente FROM veiculo";

		try (Connection conn = Conexao.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setIdVeiculo(rs.getInt("idVeiculo")); // Adicionado
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setTipo_veiculo(rs.getString("tipo_veiculo"));
				veiculo.setNome_veiculo(rs.getString("nome_veiculo"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setAno_veiculo(rs.getInt("ano_veiculo"));
				veiculo.setChassi(rs.getString("chassi"));
				veiculo.setObservacoes(rs.getString("observacoes"));
				veiculo.setIdCliente(rs.getInt("idCliente")); // Adicionado

				lista.add(veiculo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Métodos atualizar e deletar também foram ajustados para refletir o schema
	public void atualizar(Veiculo veiculo) {
		String sql = "UPDATE veiculo SET tipo_veiculo=?, nome_veiculo=?, modelo=?, ano_veiculo=?, chassi=?, observacoes=? WHERE idVeiculo=?";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getTipo_veiculo());
			stmt.setString(2,  veiculo.getNome_veiculo());
			stmt.setString(3, veiculo.getModelo());
			stmt.setInt(4, veiculo.getAno_veiculo());
			stmt.setString(5, veiculo.getChassi());
			stmt.setString(6, veiculo.getObservacoes());
			stmt.setInt(7, veiculo.getIdVeiculo()); // Usando idVeiculo para atualização

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				System.out.println("Veículo atualizado com sucesso!");
			} else {
				System.out.println("Nenhum veículo encontrado com o ID informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Deletar veículo por id, que é a chave primária
	public void deletar(int idVeiculo) {
		String sql = "DELETE FROM veiculo WHERE idVeiculo=?";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, idVeiculo);
			int rows = stmt.executeUpdate();

			if (rows > 0) {
				System.out.println("Veículo removido com sucesso!");
			} else {
				System.out.println("Nenhum veículo encontrado com o ID informado.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}