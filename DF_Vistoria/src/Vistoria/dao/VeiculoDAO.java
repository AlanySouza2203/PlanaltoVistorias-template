package Vistoria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Vistoria.DB.Conexao;
import Vistoria.model.Veiculo;

public class VeiculoDAO {

	// Cadastrar veículo (usando placa como chave única)
	public boolean  cadastrar(Veiculo veiculo) {
		String sql = "INSERT INTO veiculo (placa, tipo_veiculo, modelo, ano_veiculo, chassi, idCliente) VALUES (?, ?, ?, ?, ?,?)";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getPlaca());
			stmt.setString(2, veiculo.getTipo_veiculo());
			stmt.setString(3, veiculo.getModelo());
			stmt.setInt(4, veiculo.getAno_veiculo());
			stmt.setString(5, veiculo.getChassi());
			stmt.setInt(6, veiculo.getIdCliente());

			int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	// Listar todos os veículos
	public List<Veiculo> listarVeiculos() {
		List<Veiculo> lista = new ArrayList<>();
		String sql = "SELECT placa, tipo_veiculo, modelo, ano_veiculo, chassi FROM veiculo";

		try (Connection conn = Conexao.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setPlaca(rs.getString("placa"));
				veiculo.setTipo_veiculo(rs.getString("tipo_veiculo"));
				veiculo.setModelo(rs.getString("modelo"));
				veiculo.setAno_veiculo(rs.getInt("ano_veiculo"));
				veiculo.setChassi(rs.getString("chassi"));

				lista.add(veiculo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}

	// Atualizar veículo pelo número da placa
	public void atualizar(Veiculo veiculo) {
		String sql = "UPDATE veiculo SET tipo_veiculo=?, modelo=?, ano_veiculo=?, chassi=? WHERE placa=?";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, veiculo.getTipo_veiculo());
			stmt.setString(2, veiculo.getModelo());
			stmt.setInt(3, veiculo.getAno_veiculo());
			stmt.setString(4, veiculo.getChassi());
			stmt.setString(5, veiculo.getPlaca());

			int rows = stmt.executeUpdate();
			if (rows > 0) {
				System.out.println("Veículo atualizado com sucesso!");
			} else {
				System.out.println("Nenhum veículo encontrado com a placa informada.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Deletar veículo pela placa
	public void deletar(String placa) {
		String sql = "DELETE FROM veiculo WHERE placa=?";

		try (Connection conn = Conexao.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, placa);
			int rows = stmt.executeUpdate();

			if (rows > 0) {
				System.out.println("Veículo removido com sucesso!");
			} else {
				System.out.println("Nenhum veículo encontrado com a placa informada.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
