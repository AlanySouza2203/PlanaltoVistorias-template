package Vistoria.controller;

import Vistoria.dao.AgendamentoDAO;
import Vistoria.model.Agendamento;
import java.util.List;

public class AgendamentoController {

    private AgendamentoDAO agendamentoDAO;

    public AgendamentoController() {
        this.agendamentoDAO = new AgendamentoDAO();
    }

    /**
     * Agenda uma nova vistoria.
     *
     * @param agendamento O objeto Agendamento a ser salvo.
     */
    public void agendarVistoria(Agendamento agendamento) {
        agendamentoDAO.adicionarAgendamento(agendamento);
    }

    /**
     * Busca todos os agendamentos no banco de dados.
     *
     * @return Uma lista de objetos Agendamento.
     */
    public List<Agendamento> listarAgendamentos() {
        return agendamentoDAO.listarAgendamentos();
    }

    // Você pode adicionar mais métodos como buscarAgendamentoPorId, atualizarAgendamento, etc.
    public int contarAgendamentosPorCliente(int idCliente) {
        return agendamentoDAO.contarAgendamentosPorCliente(idCliente);
    }
    
    public int contarLaudosConcluidosPorCliente(int idCliente) {
        return agendamentoDAO.contarLaudosConcluidosPorCliente(idCliente);
    }
    
    // Busca de agendamentos de acordo com os Status
    public int contarAgendamentosAgendado() {
    	return agendamentoDAO.contarAgendamentosAgendado();
    }
    public int contarAgendamentosConcluido() {
    	return agendamentoDAO.contarAgendamentosConcluido();
    }
    public int contarAgendamentosCancelado() {
    	return agendamentoDAO.contarAgendamentosCancelado();
    }
    public int contarAgendamentosReservado() {
    	return agendamentoDAO.contarAgendamentosReservado();
    }
    
}