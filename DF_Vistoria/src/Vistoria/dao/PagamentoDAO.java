package Vistoria.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Vistoria.DB.Conexao;

public class PagamentoDAO {
    public static class PagamentoInfo {
        public int idPagamento;
        public String nomeCliente;
        public double valor;
        public String formaPagamento;
        public Date dataPagamento;

        public PagamentoInfo(int idPagamento, String nomeCliente, double valor, String formaPagamento, Date dataPagamento) {
            this.idPagamento = idPagamento;
            this.nomeCliente = nomeCliente;
            this.valor = valor;
            this.formaPagamento = formaPagamento;
            this.dataPagamento = dataPagamento;
        }
    }

    public List<PagamentoInfo> listarPagamentosPagos() {
        List<PagamentoInfo> lista = new ArrayList<>();

        String sql = """
            SELECT 
                p.idPagamento,
                c.nome AS nomeCliente,
                p.valor,
                p.forma_pagamento,
                p.data_pagamento
            FROM 
                pagamento p
            JOIN agendamento a ON p.idAgendamento = a.idAgendamento
            JOIN cliente c ON a.idCliente = c.idCliente
            JOIN vistoria v ON v.idAgendamento = a.idAgendamento
            WHERE 
                v.status_pagamento = 'Pago'
        """;

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PagamentoInfo info = new PagamentoInfo(
                    rs.getInt("idPagamento"),
                    rs.getString("nomeCliente"),
                    rs.getDouble("valor"),
                    rs.getString("forma_pagamento"),
                    rs.getDate("data_pagamento")
                );
                lista.add(info);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        

        return lista;
    }
    public boolean inserirPagamento(int idVistoria, String formaPagamento, double valor, String data) {
        String sql = "INSERT INTO pagamento (forma_pagamento, valor, data_pagamento, idAgendamento) " +
                    "SELECT ?, ?, ?, a.idAgendamento FROM vistoria v " +
                    "INNER JOIN agendamento a ON v.idAgendamento = a.idAgendamento " +
                    "WHERE v.idVistoria = ?";
        
        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, formaPagamento);
            stmt.setDouble(2, valor);
            stmt.setString(3, data);
            stmt.setInt(4, idVistoria);
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao inserir pagamento: " + e.getMessage());
            return false;
        }
    }
}