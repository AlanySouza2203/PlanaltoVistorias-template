package Vistoria.view;

import Vistoria.model.Funcionario;
import Vistoria.model.Agendamento;
import Vistoria.model.Vistoria;
import Vistoria.dao.AgendamentoDAO;
import Vistoria.controller.AgendamentoController;
import Vistoria.controller.VeiculoController;
import Vistoria.controller.VistoriaController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

/**
 * A classe DashboardVistoriador representa a interface principal do funcionário Vistoriador no sistema de vistoria.
 * Ela fornece uma dashboard com informações resumidas de agendamentos e painéis para: listar agendamentos, realizar vistorias e vizualizar relatório diário.
 */
public class DashboardVistoriador extends JFrame {

    // --- Atributos de Lógica de Negócio e Controladores ---
    private final Funcionario funcionarioLogado;
    private final AgendamentoController agendamentoController;
    private final AgendamentoDAO agendamentoDAO;
    private final VeiculoController veiculoController;
    private final VistoriaController vistoriaController; // Novo controlador para vistoria

    // --- Atributos de Componentes da Interface (UI) ---
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Labels para os cards do dashboard, que precisam ser atualizados dinamicamente
    private JLabel agendamentosValueLabel;
    private JLabel agendamentos2ValueLabel;
    private JLabel agendamentos3ValueLabel;

    // Atributos para a tela de agendamentos
    private JTable agendamentosTable;
    private DefaultTableModel agendamentosTableModel;
    private List<Agendamento> listaAgendamentosAgendados;
    private JButton btnRealizarVistoria;
    private JButton btnAtualizarTabela;

    // Atributos para a nova tela de realizar vistoria
    private Agendamento agendamentoSelecionado; // Para passar o objeto entre telas
    private JComboBox<String> resultadoComboBox;
    private JTextArea statusPagamento;
    private JTextArea observacoesTextArea;
    private JLabel clienteLabel;
    private JLabel veiculoLabel;

    // --- Atributos para a nova tela de relatório
    private JTable relatorioTable;
    private DefaultTableModel relatorioTableModel;

    // --- Paleta de Cores e Constantes de Estilo ---
    private static final Color SIDEBAR_COLOR = new Color(33, 150, 243); // Azul lateral
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Fundo cinza claro
    private static final Color CARD_BACKGROUND = Color.WHITE;
    private static final Color CARD_TITLE_ORANGE = new Color(255, 152, 0);
    private static final Color CARD_TITLE_GREEN = new Color(0, 150, 136);
    private static final Color CARD_TITLE_RED = new Color(244, 67, 54);
    private static final Color CARD_VALUE_BLUE = new Color(33, 150, 243);

    /**
     * Construtor da classe DashboardVistoriador para o objeto Vistoriador que está logado no sistema.
     */
    public DashboardVistoriador(Funcionario funcionario) {
    	this.funcionarioLogado = funcionario;
    	this.agendamentoController = new AgendamentoController();
    	this.agendamentoDAO = new AgendamentoDAO();
        this.veiculoController = new VeiculoController();
        this.vistoriaController = new VistoriaController();

    	//Configurações básicas da janela
    	setTitle("Dashboard do Vistoriador");
        setSize(1300, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Inicialização e adição dos painéis principais ---
        JPanel sidebarPanel = criarPainelSidebar();

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_COLOR);
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        cardPanel.add(criarPainelDashboardInicial(), "Dashboard");
        cardPanel.add(criarPainelAgendamento(), "Agendamento");
        cardPanel.add(criarPainelRelatorio(), "Relatorio");
        // O painel de vistoria será adicionado dinamicamente

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        // --- Ações dos botões e inicialização de dados ---
        configurarAcoesBotoes(sidebarPanel);
        atualizarCardsDashboard();

        setVisible(true);
    }

    // --- Métodos de Criação de Painéis e Componentes de UI ---

    private JPanel criarPainelSidebar() {
    	JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(SIDEBAR_COLOR);
        sidebarPanel.setPreferredSize(new Dimension(240, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(30, 20, 30, 20));

        JLabel titleLabel = new JLabel("DF-Vistoria ");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(titleLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JButton btnDashboard = criarBotaoLateral("Dashboard");
        JButton btnAgendamento = criarBotaoLateral("Agendamentos");
        JButton btnRelatorio = criarBotaoLateral("Exibir Relatório");
        JButton btnLogout = criarBotaoLateral("Sair");

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnAgendamento);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnRelatorio);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);

    	return sidebarPanel;
    }

    private void configurarAcoesBotoes(JPanel sidebarPanel) {
        JButton btnDashboard = (JButton) sidebarPanel.getComponent(2);
        JButton btnAgendamento = (JButton) sidebarPanel.getComponent(4);
        JButton btnRelatorio = (JButton) sidebarPanel.getComponent(6);
        JButton btnLogout = (JButton) sidebarPanel.getComponent(8);

        btnDashboard.addActionListener(e -> {
            cardLayout.show(cardPanel, "Dashboard");
        });
        btnAgendamento.addActionListener(e -> {
            popularTabelaAgendamentos();
            cardLayout.show(cardPanel, "Agendamento");
        });
        btnRelatorio.addActionListener(e -> {
            popularTabelaRelatorio(); // Chama o método para popular a tabela do relatório
            cardLayout.show(cardPanel, "Relatorio");
        });
        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }

    private JPanel criarPainelDashboardInicial() {
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel welcomeLabel = new JLabel("Agendamentos marcados como:");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        welcomeLabel.setForeground(Color.DARK_GRAY);
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        cardsPanel.setBackground(BACKGROUND_COLOR);

        JPanel cardAgendamento1 = criarCardInfo("Pendente");
        agendamentosValueLabel = (JLabel) ((BorderLayout) cardAgendamento1.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        JPanel cardAgendamento2 = criarCardInfo("Concluídos");
        agendamentos2ValueLabel = (JLabel) ((BorderLayout) cardAgendamento2.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        JPanel cardAgendamento3 = criarCardInfo("Cancelados");
        agendamentos3ValueLabel = (JLabel) ((BorderLayout) cardAgendamento3.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        cardsPanel.add(cardAgendamento1);
        cardsPanel.add(cardAgendamento2);
        cardsPanel.add(cardAgendamento3);

        panel.add(cardsPanel);
        return panel;
    }

    private JPanel criarCardInfo(String titulo) {
        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 25));
                g2.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 20, 20);
                g2.setColor(CARD_BACKGROUND);
                g2.fillRoundRect(0, 0, getWidth() - 10, getHeight() - 10, 20, 20);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));

        if (titulo.contains("Pendente")) {
            titleLabel.setForeground(CARD_TITLE_ORANGE);
        } else if (titulo.contains("Concluídos")) {
            titleLabel.setForeground(CARD_TITLE_GREEN);
        } else if (titulo.contains("Cancelados")) {
            titleLabel.setForeground(CARD_TITLE_RED);
        }

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 46));
        valueLabel.setForeground(CARD_VALUE_BLUE);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topPanel.setOpaque(false);
        topPanel.add(titleLabel);

        card.add(topPanel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JButton criarBotaoLateral(String texto) {
        JButton button = new JButton(texto);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(220, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(12);
        button.setBorder(new EmptyBorder(12, 20, 12, 20));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(21, 101, 192));
                button.setOpaque(true);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(SIDEBAR_COLOR);
                button.setOpaque(false);
            }
        });
        return button;
    }

    private JPanel criarPainelAgendamento() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Agendamentos marcados como: Pendente");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] colunas = {"ID", "Data", "Hora", "Cliente", "Placa do Veículo"};
        agendamentosTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        agendamentosTable = new JTable(agendamentosTableModel);
        agendamentosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        agendamentosTable.setRowHeight(30);
        agendamentosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        agendamentosTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        agendamentosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnRealizarVistoria.setEnabled(agendamentosTable.getSelectedRow() != -1);
            }
        });

        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);

        btnRealizarVistoria = new JButton("Realizar Vistoria");
        btnRealizarVistoria.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRealizarVistoria.setBackground(new Color(66, 133, 244));
        btnRealizarVistoria.setForeground(Color.WHITE);
        btnRealizarVistoria.setBorderPainted(false);
        btnRealizarVistoria.setFocusPainted(false);
        btnRealizarVistoria.setEnabled(false);
        btnRealizarVistoria.addActionListener(e -> {
            int selectedRow = agendamentosTable.getSelectedRow();
            if (selectedRow != -1) {
                agendamentoSelecionado = listaAgendamentosAgendados.get(selectedRow);
                // Prepara e exibe o painel de vistoria
                exibirPainelRealizarVistoria(agendamentoSelecionado);
            }
        });
        buttonsPanel.add(btnRealizarVistoria);

        btnAtualizarTabela = new JButton("Atualizar Tabela");
        btnAtualizarTabela.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAtualizarTabela.setBackground(new Color(52, 168, 83));
        btnAtualizarTabela.setForeground(Color.WHITE);
        btnAtualizarTabela.setBorderPainted(false);
        btnAtualizarTabela.setFocusPainted(false);
        btnAtualizarTabela.addActionListener(e -> {
            popularTabelaAgendamentos();
            JOptionPane.showMessageDialog(this, "Tabela de agendamentos atualizada!", "Atualização", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonsPanel.add(btnAtualizarTabela);

        panel.add(buttonsPanel, BorderLayout.SOUTH);
        popularTabelaAgendamentos();

        return panel;
    }

    /**
     * Cria e retorna o painel para realizar uma vistoria a partir de um agendamento.
     */
    private JPanel criarPainelRealizarVistoria() {
    	JPanel formPanel = new JPanel(new GridBagLayout());
    	formPanel.setBackground(BACKGROUND_COLOR);
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets(10, 10, 10, 10);
    	gbc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Formulário de Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 20, 0);
        formPanel.add(title, gbc);

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        // Título "Detalhes do Agendamento"
        JLabel detalhesTitle = new JLabel("Detalhes do Agendamento");
        detalhesTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        detalhesTitle.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(detalhesTitle, gbc);

        // Labels para os detalhes do agendamento
        clienteLabel = new JLabel("Cliente: ");
        clienteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(clienteLabel, gbc);

        veiculoLabel = new JLabel("Veículo: ");
        veiculoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(veiculoLabel, gbc);

        // Título "Dados da Vistoria"
        JLabel vistoriaTitle = new JLabel("Dados da Vistoria");
        vistoriaTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        vistoriaTitle.setForeground(new Color(33, 150, 243));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2; gbc.insets = new Insets(20, 10, 10, 10);
        formPanel.add(vistoriaTitle, gbc);

        // Resultado da Vistoria
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.insets = new Insets(10, 10, 10, 10);
        formPanel.add(new JLabel("Resultado:"), gbc);

        resultadoComboBox = new JComboBox<>(new String[]{"Aprovado", "Reprovado", "Aprovado com ressalvas"});
        resultadoComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 1;
        formPanel.add(resultadoComboBox, gbc);

        // Observações
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Observações:"), gbc);

        observacoesTextArea = new JTextArea(5, 20);
        observacoesTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        observacoesTextArea.setLineWrap(true);
        observacoesTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(observacoesTextArea);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 1; gbc.weighty = 1.0; gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);
        
        // CORREÇÃO: Inicializa a variável statusPagamento
        statusPagamento = new JTextArea();
        statusPagamento.setText("Pendente");

        // Botão de Submeter
        JButton btnSalvarVistoria = new JButton("Salvar Vistoria");
        btnSalvarVistoria.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnSalvarVistoria.setBackground(new Color(52, 168, 83));
        btnSalvarVistoria.setForeground(Color.WHITE);
        btnSalvarVistoria.setBorderPainted(false);
        btnSalvarVistoria.setFocusPainted(false);
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 10, 10);

        btnSalvarVistoria.addActionListener(e -> {
            String resultado = (String) resultadoComboBox.getSelectedItem();
            String observacoes = observacoesTextArea.getText();
            String status_pagamento = (String) statusPagamento.getText();

            // Lógica para salvar a vistoria no banco de dados
            try {
                vistoriaController.realizarVistoria(
                    agendamentoSelecionado,
                    funcionarioLogado,
                    resultado,
                    status_pagamento,
                    observacoes
                );
                JOptionPane.showMessageDialog(this, "Vistoria registrada com sucesso e agendamento concluído!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // Volta para a tela de agendamentos e atualiza a tabela
                popularTabelaAgendamentos();
                atualizarCardsDashboard();
                cardLayout.show(cardPanel, "Agendamento");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar a vistoria: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        formPanel.add(btnSalvarVistoria, gbc);

    	return formPanel;
    }

    /**
     * Prepara e exibe o painel de vistoria com os dados do agendamento selecionado.
     */
    private void exibirPainelRealizarVistoria(Agendamento agendamento) {
        // Limpa o painel e recria com os novos dados
        if (cardPanel.getComponentCount() > 3) {
            cardPanel.remove(3);
        }
        cardPanel.add(criarPainelRealizarVistoria(), "RealizarVistoria");

        // Atualiza os labels com as informações do agendamento
        clienteLabel.setText("Cliente: " + agendamento.getCliente().getNome() + " (ID: " + agendamento.getCliente().getIdCliente() + ")");
        veiculoLabel.setText("Veículo: " + agendamento.getVeiculo().getNome_veiculo() + " (" + agendamento.getVeiculo().getPlaca() + ")");
        observacoesTextArea.setText("");
        resultadoComboBox.setSelectedIndex(0);

        cardLayout.show(cardPanel, "RealizarVistoria");
    }

    private JPanel criarPainelRelatorio() {
    	JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Relatório de Vistorias Concluídas");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(new EmptyBorder(0, 0, 20, 0));
        panel.add(title, BorderLayout.NORTH);

        String[] colunas = {"ID Vistoria", "Data", "Resultado", "Cliente", "Placa do Veículo"};
        relatorioTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        relatorioTable = new JTable(relatorioTableModel);
        relatorioTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        relatorioTable.setRowHeight(30);
        relatorioTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        relatorioTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(relatorioTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void popularTabelaRelatorio() {
        relatorioTableModel.setRowCount(0); // Limpa a tabela
        // Usa o controller para obter a lista de vistorias do funcionário logado
        // A lógica do DAO e Controller já garante que apenas vistorias de agendamentos concluídos serão retornadas.
        List<Vistoria> vistoriasDoFuncionario = vistoriaController.getRelatorioVistorias(funcionarioLogado);

        for (Vistoria vistoria : vistoriasDoFuncionario) {
            relatorioTableModel.addRow(new Object[]{
                vistoria.getIdVistoria(),
                vistoria.getData_vistoria(),
                vistoria.getResultado(),
                vistoria.getAgendamento().getCliente().getNome(),
                vistoria.getAgendamento().getVeiculo().getPlaca()
            });
        }
    }

    private void atualizarCardsDashboard() {
        int numAgendamentoAgendado = agendamentoDAO.contarAgendamentosAgendado();
        int numAgendamentoConcluido = agendamentoDAO.contarAgendamentosConcluido();
        int numAgendamentoCancelado = agendamentoDAO.contarAgendamentosCancelado();

        if (agendamentosValueLabel != null) {
            agendamentosValueLabel.setText(String.valueOf(numAgendamentoAgendado));
        }
        if (agendamentos2ValueLabel != null) {
            agendamentos2ValueLabel.setText(String.valueOf(numAgendamentoConcluido));
        }
        if (agendamentos3ValueLabel != null) {
            agendamentos3ValueLabel.setText(String.valueOf(numAgendamentoCancelado));
        }
    }

    private void popularTabelaAgendamentos() {
        agendamentosTableModel.setRowCount(0);
        listaAgendamentosAgendados = new ArrayList<>();
        listaAgendamentosAgendados = agendamentoDAO.listarAgendamentosAgendadosComDetalhes();

        for (Agendamento agendamento : listaAgendamentosAgendados) {
            agendamentosTableModel.addRow(new Object[]{
                agendamento.getIdAgendamento(),
                agendamento.getData_agendamento(),
                agendamento.getHora(),
                agendamento.getCliente().getNome(),
                agendamento.getVeiculo().getPlaca()
            });
        }
    }

    private ImageIcon carregarIcone(String path, int largura, int altura) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

	public static void main(String[] args) {
	}
}