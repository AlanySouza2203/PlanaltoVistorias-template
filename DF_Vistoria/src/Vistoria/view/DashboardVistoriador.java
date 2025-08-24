package Vistoria.view;

import Vistoria.model.Funcionario;
import Vistoria.model.Agendamento;
import Vistoria.model.Cliente;
import Vistoria.model.Veiculo;
import Vistoria.dao.AgendamentoDAO;
import Vistoria.controller.VeiculoController;
import Vistoria.controller.AgendamentoController;
import Vistoria.controller.FuncionarioController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * A classe DashboardVistoriador representa a interface principal do funcionário Vistoriador no sistema de vistoria.
 * Ela fornece uma dashboard com informações resumidas e painéis para listar agendamentos, Realizar vistorias e vizualizar relatório diário.
 */

public class DashboardVistoriador extends JFrame {
	
	// --- Atributos de Lógica de Negócio e Controladores ---
    private final Funcionario funcionarioLogado;
    private final AgendamentoController agendamentoController;
    private final AgendamentoDAO agendamentoDAO;
    private final VeiculoController veiculoController;

    // --- Atributos de Componentes da Interface (UI) ---
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Labels para os cards do dashboard, que precisam ser atualizados dinamicamente
    private JLabel agendamentosValueLabel;
    private JLabel agendamentos2ValueLabel;
    private JLabel agendamentos3ValueLabel;
    private JLabel agendamentos4ValueLabel;
    
    // Atributos para a nova tela de agendamentos
    private JTable agendamentosTable;
    private DefaultTableModel agendamentosTableModel;
    private List<Agendamento> listaAgendamentosAgendados;
    private JButton btnRealizarVistoria;
    private JButton btnAtualizarTabela;
    
    // --- Paleta de Cores e Constantes de Estilo ---
    private static final Color SIDEBAR_COLOR = new Color(33, 150, 243); // Azul lateral
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Fundo cinza claro
    private static final Color CARD_BACKGROUND = Color.WHITE;

    // Cores dos títulos dos cards
    private static final Color CARD_TITLE_ORANGE = new Color(255, 152, 0);
    private static final Color CARD_TITLE_GREEN = new Color(0, 150, 136);
    private static final Color CARD_TITLE_RED = new Color(244, 67, 54);
    
    // Cor dos números nos cards
    private static final Color CARD_VALUE_BLUE = new Color(33, 150, 243);
    
    
    /**
     * Construtor da classe DashboardVistoriador para o objeto Vistoriador que está logado no sistema.
     */
    
    public DashboardVistoriador(Funcionario funcionario) {
    	this.funcionarioLogado = funcionario;
    	this.agendamentoController = new AgendamentoController();
    	this.agendamentoDAO = new AgendamentoDAO();
        this.veiculoController = new VeiculoController();
    	
    	//Configurações básicas da janela
    	setTitle("Dashboard do Vistoriador");
        setSize(1100, 750);
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
        cardPanel.add(criarPainelRealizarVistoria(), "RealizarVistoria");
        cardPanel.add(criarPainelRelatorio(), "Relatorio");

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        
        // --- Ações dos botões e inicialização de dados ---
        configurarAcoesBotoes(sidebarPanel);
        atualizarCardsDashboard();
        
        setVisible(true);
    }
    // --- Métodos de Criação de Painéis e Componentes de UI ---

    /**
     * Cria e retorna o painel da barra lateral.
     *
     * @return O JPanel da barra lateral.
     */
    
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
        JButton btnVistoria = criarBotaoLateral("Realizar Vistoria");
        JButton btnRelatorio = criarBotaoLateral("Exibir Relatório");
        JButton btnLogout = criarBotaoLateral("Sair");

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnAgendamento);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnVistoria);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnRelatorio);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);
        
    	return sidebarPanel;
    }
    
    /**
     * Configura as ações de clique para os botões da barra lateral.
     *
     * @param sidebarPanel O painel da barra lateral que contém os botões.
     */
    private void configurarAcoesBotoes(JPanel sidebarPanel) {
    	// Encontra os botões do painel lateral para configurar as ações
        JButton btnDashboard = (JButton) sidebarPanel.getComponent(2);
        JButton btnAgendamento = (JButton) sidebarPanel.getComponent(4);
        JButton btnVistoria = (JButton) sidebarPanel.getComponent(6);
        JButton btnRelatorio = (JButton) sidebarPanel.getComponent(8);
        JButton btnLogout = (JButton) sidebarPanel.getComponent(10);
        
        btnDashboard.addActionListener(e -> {
            cardLayout.show(cardPanel, "Dashboard");
        });
        btnAgendamento.addActionListener(e -> {
            popularTabelaAgendamentos();
            cardLayout.show(cardPanel, "Agendamento");
        });
        btnVistoria.addActionListener(e -> cardLayout.show(cardPanel, "RealizarVistoria"));
        btnRelatorio.addActionListener(e -> cardLayout.show(cardPanel, "Relatorio"));
        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });
    }
    /**
     * Cria e retorna o painel inicial do dashboard, com os cards de informação.
     *
     * @return O JPanel do dashboard inicial.
     */
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

        JPanel cardAgendamento1 = criarCardInfo("Agendado");
        agendamentosValueLabel = (JLabel) ((BorderLayout) cardAgendamento1.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        JPanel cardAgendamento2 = criarCardInfo("Concluídos");
        agendamentos2ValueLabel = (JLabel) ((BorderLayout) cardAgendamento2.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        JPanel cardAgendamento3 = criarCardInfo("Cancelados");
        agendamentos3ValueLabel = (JLabel) ((BorderLayout) cardAgendamento3.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        JPanel cardAgendamento4 = criarCardInfo("Reservados");
        agendamentos4ValueLabel = (JLabel) ((BorderLayout) cardAgendamento4.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        cardsPanel.add(cardAgendamento1);
        cardsPanel.add(cardAgendamento2);
        cardsPanel.add(cardAgendamento3);
        cardsPanel.add(cardAgendamento4);
        
        panel.add(cardsPanel);
        return panel;
    }
    /**
     * Cria um card de informação estilizado com ícone, título e valor.
     *
     * @param titulo O texto do título do card.
     * @param iconPath O caminho do ícone.
     * @return O JPanel do card de informação.
     */
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
        
        if (titulo.contains("Agendado")) {
            titleLabel.setForeground(CARD_TITLE_ORANGE);
        } else if (titulo.contains("Concluídos")) {
            titleLabel.setForeground(CARD_TITLE_GREEN);
        } else if (titulo.contains("Cancelados")) {
            titleLabel.setForeground(CARD_TITLE_RED);
        } else if (titulo.contains("Reservados")) {
            titleLabel.setForeground(CARD_VALUE_BLUE);
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
    /**
     * Cria e retorna um botão estilizado para a barra lateral.
     *
     * @param texto O texto do botão.
     * @param icon O ícone do botão.
     * @return O JButton estilizado.
     */
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

        // Efeito de hover
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
    /**
     * Cria e retorna o painel para vizualizar as vistorias cadastradas no sistema.
     *
     * @return O JPanel do formulário de agendamento.
     */
    private JPanel criarPainelAgendamento() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        // Título do painel
        JLabel title = new JLabel("Agendamentos marcados como: Agendado");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        title.setBorder(new EmptyBorder(0, 0, 20, 0)); // Espaçamento inferior
        panel.add(title, BorderLayout.NORTH);

        // Configuração da tabela
        String[] colunas = {"ID", "Data", "Hora", "Cliente", "Placa do Veículo"};
        agendamentosTableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Desabilita a edição das células da tabela
                return false;
            }
        };
        agendamentosTable = new JTable(agendamentosTableModel);
        agendamentosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        agendamentosTable.setRowHeight(30);
        agendamentosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        agendamentosTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Adiciona a funcionalidade de selecionar apenas uma linha
        agendamentosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnRealizarVistoria.setEnabled(agendamentosTable.getSelectedRow() != -1);
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(agendamentosTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove a borda padrão do scroll pane
        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel para os botões na parte inferior
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonsPanel.setBackground(BACKGROUND_COLOR);

        // Botão Realizar Vistoria
        btnRealizarVistoria = new JButton("Realizar Vistoria");
        btnRealizarVistoria.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRealizarVistoria.setBackground(new Color(66, 133, 244)); // Google Blue
        btnRealizarVistoria.setForeground(Color.WHITE);
        btnRealizarVistoria.setBorderPainted(false);
        btnRealizarVistoria.setFocusPainted(false);
        btnRealizarVistoria.setEnabled(false); // Inicia desabilitado
        btnRealizarVistoria.addActionListener(e -> {
            int selectedRow = agendamentosTable.getSelectedRow();
            if (selectedRow != -1) {
                // Obtém o objeto Agendamento selecionado da lista
                Agendamento agendamentoSelecionado = listaAgendamentosAgendados.get(selectedRow);
                // Aqui sai da tela de agendamentos e segue para realizar vistoria.
                JOptionPane.showMessageDialog(this, "Preparando para realizar vistoria do agendamento ID: " + agendamentoSelecionado.getIdAgendamento(), "Ação", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonsPanel.add(btnRealizarVistoria);

        // Botão Atualizar Tabela
        btnAtualizarTabela = new JButton("Atualizar Tabela");
        btnAtualizarTabela.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnAtualizarTabela.setBackground(new Color(52, 168, 83)); // Google Green
        btnAtualizarTabela.setForeground(Color.WHITE);
        btnAtualizarTabela.setBorderPainted(false);
        btnAtualizarTabela.setFocusPainted(false);
        btnAtualizarTabela.addActionListener(e -> {
            popularTabelaAgendamentos();
            JOptionPane.showMessageDialog(this, "Tabela de agendamentos atualizada!", "Atualização", JOptionPane.INFORMATION_MESSAGE);
        });
        buttonsPanel.add(btnAtualizarTabela);

        panel.add(buttonsPanel, BorderLayout.SOUTH);

        // Chama o método para popular a tabela com os dados iniciais
        popularTabelaAgendamentos();

        return panel;
    }
    
    /**
     * Cria e retorna o painel para realizar uma vistoria a partir de um agendamento.
     *
     * @return O JPanel do formulário de agendamento.
     */
    private JPanel criarPainelRealizarVistoria() {
    	JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Realizar Vistoria?");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);
        
        return panel;
    }
    /**
     * Cria e retorna o painel para vizualizar um relatório de vistorias realizadas.
     *
     * @return O JPanel do formulário de agendamento.
     */
    private JPanel criarPainelRelatorio() {
    	JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Relatórios");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);
        
        return panel;
    }
    
    /**
     * Atualiza os labels dos cards do dashboard com dados do banco de dados.
     * Isso é chamado no início e após cada ação de sucesso, como agendar um novo serviço.
     */
    private void atualizarCardsDashboard() {
        int numVeiculos = veiculoController.contarVeiculosPorCliente(funcionarioLogado.getIdFuncionario());
        int numAgendamentos = agendamentoController.contarAgendamentosPorCliente(funcionarioLogado.getIdFuncionario());
        int numLaudos = agendamentoController.contarLaudosConcluidosPorCliente(funcionarioLogado.getIdFuncionario());
        int numAgendamentoAgendado = agendamentoController.contarAgendamentosAgendado();
        int numAgendamentoConcluido = agendamentoController.contarAgendamentosConcluido();
        int numAgendamentoCancelado = agendamentoController.contarAgendamentosCancelado();
        int numAgendamentoReservado = agendamentoController.contarAgendamentosReservado();
        
        // Atualiza os labels com os valores reais
        if (agendamentosValueLabel != null) {
            agendamentosValueLabel.setText(String.valueOf(numAgendamentoAgendado));
        }
        if (agendamentos2ValueLabel != null) {
            agendamentos2ValueLabel.setText(String.valueOf(numAgendamentoConcluido));
        }
        if (agendamentos3ValueLabel != null) {
            agendamentos3ValueLabel.setText(String.valueOf(numAgendamentoCancelado));
        }
        if (agendamentos4ValueLabel != null) {
        	agendamentos4ValueLabel.setText(String.valueOf(numAgendamentoReservado));
        }
    }

    /**
     * Popula a tabela de agendamentos com os dados do banco de dados.
     */
    private void popularTabelaAgendamentos() {
        // Limpa os dados existentes na tabela
        agendamentosTableModel.setRowCount(0);
        listaAgendamentosAgendados = new ArrayList<>();

        // Busca os agendamentos no banco de dados
        listaAgendamentosAgendados = agendamentoDAO.listarAgendamentosAgendadosComDetalhes();

        // Preenche a tabela com os dados dos agendamentos
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
    
    // --- Métodos de Utilidade (Helpers) ---

    /**
     * Carrega um ícone a partir do caminho especificado e o redimensiona.
     *
     * @param path O caminho do arquivo do ícone.
     * @param largura A largura desejada para o ícone.
     * @param altura A altura desejada para o ícone.
     * @return O ImageIcon redimensionado.
     */
    private ImageIcon carregarIcone(String path, int largura, int altura) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }
    
    /**
     * Método principal para iniciar a aplicação, útil para testes.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
	public static void main(String[] args) {
        Funcionario funcionario = new Funcionario(4, "João da Silva", "joao@email.com",
                "F-0001", "senha", "999999999");
		SwingUtilities.invokeLater(() -> new DashboardVistoriador(funcionario));
	}

}
