package Vistoria.controller;

import Vistoria.dao.VistoriaDAO;
import Vistoria.dao.AgendamentoDAO;
import Vistoria.model.Vistoria;
import Vistoria.model.Agendamento;
import Vistoria.model.Funcionario;
import Vistoria.model.Cliente;
import java.time.LocalDate;
import java.util.List;

public class VistoriaController {
    private final VistoriaDAO vistoriaDAO;
    private final AgendamentoDAO agendamentoDAO;

    public VistoriaController() {
        this.vistoriaDAO = new VistoriaDAO();
        this.agendamentoDAO = new AgendamentoDAO();
    }

    /**
     * Realiza a vistoria, salvando-a no banco e atualizando o status do agendamento.
     * @param agendamento O agendamento a ser concluído.
     * @param funcionario O funcionário que realizou a vistoria.
     * @param resultado O resultado da vistoria.
     * @param observacoes As observações da vistoria.
     */
    public void realizarVistoria(Agendamento agendamento, Funcionario funcionario, String resultado, String status_pagamento, String observacoes) throws Exception {
        try {
            // Cria o objeto Vistoria
            Vistoria novaVistoria = new Vistoria();
            novaVistoria.setData_vistoria(LocalDate.now().toString());
            novaVistoria.setResultado(resultado);
            novaVistoria.setStatus_pagamento(status_pagamento);
            novaVistoria.setObservacoes(observacoes);
            novaVistoria.setAgendamento(agendamento);
            novaVistoria.setFuncionario(funcionario);

            // Salva a vistoria no banco de dados
            boolean vistoriaSalva = vistoriaDAO.inserirVistoria(novaVistoria);

            if (vistoriaSalva) {
                // Se a vistoria foi salva, atualiza o status do agendamento
                boolean agendamentoAtualizado = agendamentoDAO.atualizarStatusAgendamento(agendamento.getIdAgendamento(), "Concluido");
                if (!agendamentoAtualizado) {
                    throw new Exception("Vistoria salva, mas falha ao atualizar o status do agendamento.");
                }
            } else {
                throw new Exception("Falha ao salvar a vistoria no banco de dados.");
            }
        } catch (Exception e) {
            throw new Exception("Erro durante a realização da vistoria: " + e.getMessage());
        }
    }
    /**
     * Obtém um relatório de vistorias de um funcionário específico.
     * @param funcionario O objeto Funcionario para o qual o relatório é gerado.
     * @return Uma lista de objetos Vistoria.
     */
    public List<Vistoria> getRelatorioVistorias(Funcionario funcionario) {
        return vistoriaDAO.listarVistoriasPorFuncionario(funcionario.getIdFuncionario());
    }
    
    public List<Vistoria> getVistoriasPorCliente(Cliente cliente) {
        return vistoriaDAO.listarVistoriasPorCliente(cliente.getIdCliente());
    }
}
