package Vistoria.view;

import Vistoria.controller.AgendamentoController;
import Vistoria.controller.VeiculoController;
import Vistoria.model.Agendamento;
import Vistoria.model.Cliente;
import Vistoria.model.Veiculo;
import Vistoria.controller.VistoriaController;
import Vistoria.model.Vistoria;
import Vistoria.dao.PagamentoDAO;
import Vistoria.dao.VistoriaDAO;
import Vistoria.model.PagamentoController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A classe DashboardCliente representa a interface principal do cliente no sistema de vistoria.
 * Ela fornece uma dashboard com informações resumidas e painéis para agendar vistorias,
 * cadastrar veículos e emitir laudos.
 */
public class DashboardCliente extends JFrame {

    // --- Atributos de Lógica de Negócio e Controladores ---
    private final Cliente clienteLogado;
    private final AgendamentoController agendamentoController;
    private final VeiculoController veiculoController;

    // --- Atributos de Componentes da Interface (UI) ---
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Campos de formulário
    private JComboBox<String> veiculoAgendarComboBox;
    private final Map<String, Veiculo> veiculoMap = new HashMap<>();

    // Labels para os cards do dashboard, que precisam ser atualizados dinamicamente
    private JLabel agendamentosValueLabel;
    private JLabel laudosValueLabel;
    private JLabel veiculosValueLabel;

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
     * Construtor da classe DashboardCliente.
     *
     * @param cliente O objeto Cliente que está logado no sistema.
     */
    public DashboardCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        this.agendamentoController = new AgendamentoController();
        this.veiculoController = new VeiculoController();

        // --- Configurações básicas da janela ---
        setTitle("Dashboard do Cliente");
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
        cardPanel.add(criarPainelAgendarVistoria(), "AgendarVistoria");
        cardPanel.add(criarPainelCadastrarVeiculo(), "CadastrarVeiculo");
        cardPanel.add(criarPainelEmitirLaudo(), "EmitirLaudo");

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

        JButton btnDashboard = criarBotaoLateral("Dashboard", carregarIcone("/icones/dashboard.png", 24, 24));
        JButton btnAgendar = criarBotaoLateral("Agendar Vistoria", carregarIcone("/icones/calendario.png", 24, 24));
        JButton btnCadastrar = criarBotaoLateral("Cadastrar Veículo", carregarIcone("/icones/carro.png", 24, 24));
        JButton btnLaudos = criarBotaoLateral("Emitir Laudo", carregarIcone("/icones/emitir.png", 24, 24));
        JButton btnLogout = criarBotaoLateral("Sair", carregarIcone("/icones/saida.png", 24, 24));

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnAgendar);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnCadastrar);
        sidebarPanel.add(Box.createVerticalStrut(15));
        sidebarPanel.add(btnLaudos);
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
        JButton btnAgendar = (JButton) sidebarPanel.getComponent(4);
        JButton btnCadastrar = (JButton) sidebarPanel.getComponent(6);
        JButton btnLaudos = (JButton) sidebarPanel.getComponent(8);
        JButton btnLogout = (JButton) sidebarPanel.getComponent(10);
        
        btnDashboard.addActionListener(e -> {
            atualizarCardsDashboard();
            cardLayout.show(cardPanel, "Dashboard");
        });
        btnAgendar.addActionListener(e -> {
            carregarVeiculosComboBox();
            cardLayout.show(cardPanel, "AgendarVistoria");
        });
        btnCadastrar.addActionListener(e -> cardLayout.show(cardPanel, "CadastrarVeiculo"));
        btnLaudos.addActionListener(e -> cardLayout.show(cardPanel, "EmitirLaudo"));
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

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + clienteLogado.getNome() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
        welcomeLabel.setForeground(Color.DARK_GRAY);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        cardsPanel.setBackground(BACKGROUND_COLOR);

        JPanel cardAgendamentos = criarCardInfo("Vistorias Agendadas", "/icones/task.png");
        agendamentosValueLabel = (JLabel) ((BorderLayout) cardAgendamentos.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        JPanel cardLaudos = criarCardInfo("Laudos Concluídos", "/icones/check.png");
        laudosValueLabel = (JLabel) ((BorderLayout) cardLaudos.getLayout()).getLayoutComponent(BorderLayout.CENTER);
        
        JPanel cardVeiculos = criarCardInfo("Veículos Cadastrados", "/icones/carro.png");
        veiculosValueLabel = (JLabel) ((BorderLayout) cardVeiculos.getLayout()).getLayoutComponent(BorderLayout.CENTER);

        cardsPanel.add(cardAgendamentos);
        cardsPanel.add(cardLaudos);
        cardsPanel.add(cardVeiculos);
        
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
    private JPanel criarCardInfo(String titulo, String iconPath) {
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

        JLabel iconLabel = new JLabel(carregarIcone(iconPath, 32, 32));
        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        if (titulo.contains("Agendadas")) {
            titleLabel.setForeground(CARD_TITLE_ORANGE);
        } else if (titulo.contains("Concluídos")) {
            titleLabel.setForeground(CARD_TITLE_GREEN);
        } else if (titulo.contains("Cadastrados")) {
            titleLabel.setForeground(CARD_TITLE_RED);
        }

        JLabel valueLabel = new JLabel("0");
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 46));
        valueLabel.setForeground(CARD_VALUE_BLUE);
        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        topPanel.setOpaque(false);
        topPanel.add(iconLabel);
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
    private JButton criarBotaoLateral(String texto, ImageIcon icon) {
        JButton button = new JButton(texto, icon);
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
     * Cria e retorna o painel para agendar uma nova vistoria.
     *
     * @return O JPanel do formulário de agendamento.
     */
    private JPanel criarPainelAgendarVistoria() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Agendar Nova Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);

        JTextField dataField = new JTextField(20);
        JTextField horaField = new JTextField(20);
        JComboBox<String> tipoVistoria = new JComboBox<>(new String[]{"Transferência", "Cautelar", "Estrutural"});
        veiculoAgendarComboBox = new JComboBox<>();
        JButton agendarButton = new JButton("Agendar Vistoria");

        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 1; panel.add(new JLabel("Data:"), gbc);
        gbc.gridx = 1; panel.add(dataField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Hora:"), gbc);
        gbc.gridx = 1; panel.add(horaField, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Tipo de Vistoria:"), gbc);
        gbc.gridx = 1; panel.add(tipoVistoria, gbc);
        gbc.gridy = 4; gbc.gridx = 0; panel.add(new JLabel("Veículo:"), gbc);
        gbc.gridx = 1; panel.add(veiculoAgendarComboBox, gbc);
        gbc.gridy = 5; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(agendarButton, gbc);

        // Lógica para agendar a vistoria
        agendarButton.addActionListener(e -> {
            String data = dataField.getText();
            String hora = horaField.getText();
            String tipo = (String) tipoVistoria.getSelectedItem();
            String veiculoSelecionadoStr = (String) veiculoAgendarComboBox.getSelectedItem();

            if (data.isEmpty() || hora.isEmpty() || veiculoSelecionadoStr == null || veiculoSelecionadoStr.contains("Nenhum veículo")) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos e selecione um veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Veiculo veiculoSelecionado = veiculoMap.get(veiculoSelecionadoStr);
            if (veiculoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Veículo não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Agendamento novoAgendamento = new Agendamento();
            novoAgendamento.setData_agendamento(data);
            novoAgendamento.setHora(hora);
            novoAgendamento.setStatus_agendamento("Pendente");
            novoAgendamento.setIdCliente(clienteLogado.getIdCliente());
            novoAgendamento.setIdVeiculo(veiculoSelecionado.getIdVeiculo());

            agendamentoController.agendarVistoria(novoAgendamento);

            JOptionPane.showMessageDialog(this, "Vistoria agendada com sucesso para " + data + " às " + hora + "!");
            dataField.setText("");
            horaField.setText("");
            atualizarCardsDashboard();
        });

        return panel;
    }

    /**
     * Cria e retorna o painel para cadastrar um novo veículo.
     *
     * @return O JPanel do formulário de cadastro de veículo.
     */
    private JPanel criarPainelCadastrarVeiculo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Cadastrar Veículo");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);

        JTextField placaField = new JTextField(20);
        JComboBox<String> tipoVeiculoComboBox = new JComboBox<>(new String[]{"Carro", "Moto", "Caminhão"});
        JTextField nomeVeiculoField = new JTextField(20);
        JTextField modeloField = new JTextField(20);
        JTextField anoVeiculoField = new JTextField(20);
        JTextField chassiField = new JTextField(20);
        JTextArea observacoesArea = new JTextArea(3, 20);
        observacoesArea.setLineWrap(true);
        observacoesArea.setWrapStyleWord(true);
        JScrollPane observacoesScrollPane = new JScrollPane(observacoesArea);
        JButton cadastrarButton = new JButton("Cadastrar Veículo");

        gbc.gridy = 1; gbc.gridx = 0; gbc.gridwidth = 1; panel.add(new JLabel("Placa:"), gbc);
        gbc.gridx = 1; panel.add(placaField, gbc);
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Tipo de Veículo:"), gbc);
        gbc.gridx = 1; panel.add(tipoVeiculoComboBox, gbc);
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Nome do Veículo:"), gbc);
        gbc.gridx = 1; panel.add(nomeVeiculoField, gbc);
        gbc.gridy = 4; gbc.gridx = 0; panel.add(new JLabel("Modelo:"), gbc);
        gbc.gridx = 1; panel.add(modeloField, gbc);
        gbc.gridy = 5; gbc.gridx = 0; panel.add(new JLabel("Ano:"), gbc);
        gbc.gridx = 1; panel.add(anoVeiculoField, gbc);
        gbc.gridy = 6; gbc.gridx = 0; panel.add(new JLabel("Chassi:"), gbc);
        gbc.gridx = 1; panel.add(chassiField, gbc);
        gbc.gridy = 7; gbc.gridx = 0; panel.add(new JLabel("Observações:"), gbc);
        gbc.gridx = 1; panel.add(observacoesScrollPane, gbc);
        gbc.gridy = 8; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(cadastrarButton, gbc);

        cadastrarButton.addActionListener(e -> {
            try {
                String placa = placaField.getText();
                String tipo = (String) tipoVeiculoComboBox.getSelectedItem();
                String nome = nomeVeiculoField.getText();
                String modelo = modeloField.getText();
                int ano = Integer.parseInt(anoVeiculoField.getText());
                String chassi = chassiField.getText();
                String observacoes = observacoesArea.getText();

                if (placa.isEmpty() || nome.isEmpty() || modelo.isEmpty() || chassi.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Veiculo novoVeiculo = new Veiculo(placa, tipo, nome, modelo, ano, chassi, observacoes, clienteLogado.getIdCliente());
                boolean sucesso = veiculoController.cadastrarVeiculo(novoVeiculo);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Veículo " + placa + " cadastrado com sucesso!");
                    placaField.setText("");
                    nomeVeiculoField.setText("");
                    modeloField.setText("");
                    anoVeiculoField.setText("");
                    chassiField.setText("");
                    observacoesArea.setText("");
                    atualizarCardsDashboard();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar o veículo. Verifique os dados e tente novamente.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O campo 'Ano' deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    /**
     * Cria e retorna o painel para listar e emitir laudos.
     *
     * @return O JPanel do painel de laudos.
     */
    private JPanel criarPainelEmitirLaudo() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Laudos de Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        panel.add(title, BorderLayout.NORTH);

        // Tabela para exibir os laudos - ADICIONANDO COLUNA DE STATUS_PAGAMENTO
        String[] colunas = {"ID Vistoria", "Data", "Resultado", "Veículo", "Placa", "Vistoriador", "Status Pagamento"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        JTable laudosTable = new JTable(tableModel);
        laudosTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        laudosTable.setRowHeight(30);
        laudosTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JScrollPane scrollPane = new JScrollPane(laudosTable);
        
        // Botões
        JButton btnAtualizar = new JButton("Atualizar Lista");
        JButton btnEmitirPDF = new JButton("Emitir PDF");
        JButton btnRealizarPagamento = new JButton("Realizar Pagamento");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(btnAtualizar);
        buttonPanel.add(btnEmitirPDF);
        buttonPanel.add(btnRealizarPagamento);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Carregar dados inicialmente
        carregarLaudosCliente(tableModel);

        // Ação do botão atualizar
        btnAtualizar.addActionListener(e -> {
            carregarLaudosCliente(tableModel);
            JOptionPane.showMessageDialog(this, "Lista de laudos atualizada!", "Atualização", 
                JOptionPane.INFORMATION_MESSAGE);
        });

        // Ação do botão emitir PDF - SOMENTE SE STATUS FOR "Pago"
        btnEmitirPDF.addActionListener(e -> {
            int selectedRow = laudosTable.getSelectedRow();
            if (selectedRow != -1) {
                String statusPagamento = (String) tableModel.getValueAt(selectedRow, 6);
                if ("Pago".equals(statusPagamento)) {
                    int idVistoria = (Integer) tableModel.getValueAt(selectedRow, 0);
                    JOptionPane.showMessageDialog(this, 
                        "Emitindo PDF do laudo ID: " + idVistoria, 
                        "Emissão de Laudo", 
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Não é possível emitir o PDF. O pagamento está pendente.", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um laudo para emitir o PDF.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        // Ação do botão realizar pagamento
        btnRealizarPagamento.addActionListener(e -> {
            int selectedRow = laudosTable.getSelectedRow();
            if (selectedRow != -1) {
                int idVistoria = (Integer) tableModel.getValueAt(selectedRow, 0);
                String statusPagamento = (String) tableModel.getValueAt(selectedRow, 6);
                
                if ("Pendente".equals(statusPagamento)) {
                    abrirFormularioPagamento(idVistoria, tableModel, selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Este laudo já foi pago.", 
                        "Informação", 
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Selecione um laudo para realizar o pagamento.", 
                    "Aviso", 
                    JOptionPane.WARNING_MESSAGE);
            }
        });

        return panel;
    }

    // --- Métodos de Lógica de Negócio ---

    /**
     * Carrega os veículos do banco de dados para o cliente logado e popula o JComboBox.
     * Também limpa e preenche o mapeamento de placas para veículos.
     */
    
    
    private void carregarVeiculosComboBox() {
        veiculoAgendarComboBox.removeAllItems();
        veiculoMap.clear();

        List<Veiculo> veiculos = veiculoController.listarVeiculosPorCliente(clienteLogado.getIdCliente());
        if (veiculos.isEmpty()) {
            veiculoAgendarComboBox.addItem("Nenhum veículo cadastrado");
            veiculoAgendarComboBox.setEnabled(false);
        } else {
            veiculoAgendarComboBox.setEnabled(true);
            for (Veiculo v : veiculos) {
                String item = v.getPlaca() + " - " + v.getNome_veiculo();
                veiculoAgendarComboBox.addItem(item);
                veiculoMap.put(item, v); // Mapeia a string exibida para o objeto Veiculo
            }
        }
    }
    
    /**
     * atualiza a lista de emitir laudos
     */
    
    private void carregarLaudosCliente(DefaultTableModel tableModel) {
        tableModel.setRowCount(0); // Limpa a tabela
        
        // Usa o controller para obter as vistorias do cliente
        VistoriaController vistoriaController = new VistoriaController();
        List<Vistoria> vistorias = vistoriaController.getVistoriasPorCliente(clienteLogado);
        
        for (Vistoria vistoria : vistorias) {
            tableModel.addRow(new Object[]{
                vistoria.getIdVistoria(),
                vistoria.getData_vistoria(),
                vistoria.getResultado(),
                vistoria.getAgendamento().getVeiculo().getNome_veiculo(),
                vistoria.getAgendamento().getVeiculo().getPlaca(),
                vistoria.getFuncionario().getNome(),
                vistoria.getStatus_pagamento() // NOVO CAMPO ADICIONADO
            });
        }
        
        if (vistorias.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Nenhum laudo encontrado para este cliente.", 
                "Informação", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Atualiza os labels dos cards do dashboard com dados do banco de dados.
     * Isso é chamado no início e após cada ação de sucesso, como agendar um novo serviço.
     */
    private void atualizarCardsDashboard() {
        int numVeiculos = veiculoController.contarVeiculosPorCliente(clienteLogado.getIdCliente());
        int numAgendamentos = agendamentoController.contarAgendamentosPorCliente(clienteLogado.getIdCliente());
        int numLaudos = agendamentoController.contarLaudosConcluidosPorCliente(clienteLogado.getIdCliente());
        
        // Atualiza os labels com os valores reais
        if (agendamentosValueLabel != null) {
            agendamentosValueLabel.setText(String.valueOf(numAgendamentos));
        }
        if (laudosValueLabel != null) {
            laudosValueLabel.setText(String.valueOf(numLaudos));
        }
        if (veiculosValueLabel != null) {
            veiculosValueLabel.setText(String.valueOf(numVeiculos));
        }
    }
    
    private void abrirFormularioPagamento(int idVistoria, DefaultTableModel tableModel, int selectedRow) {
        JDialog dialog = new JDialog(this, "Realizar Pagamento", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(0, 2, 10, 10));
        dialog.setLocationRelativeTo(this);

        JComboBox<String> formaPagamento = new JComboBox<>(new String[]{"Débito", "Crédito", "Pix", "Boleto", "Dinheiro"});
        JTextField valorField = new JTextField();
        JTextField dataField = new JTextField(java.time.LocalDate.now().toString());

        dialog.add(new JLabel("Forma de Pagamento:"));
        dialog.add(formaPagamento);
        dialog.add(new JLabel("Valor:"));
        dialog.add(valorField);
        dialog.add(new JLabel("Data:"));
        dialog.add(dataField);

        JButton confirmarBtn = new JButton("Confirmar Pagamento");
        JButton cancelarBtn = new JButton("Cancelar");

        dialog.add(confirmarBtn);
        dialog.add(cancelarBtn);

        confirmarBtn.addActionListener(e -> {
            try {
                String forma = (String) formaPagamento.getSelectedItem();
                double valor = Double.parseDouble(valorField.getText());
                String data = dataField.getText();

                // Aqui você precisará criar um PagamentoController e PagamentoDAO
                boolean sucesso = realizarPagamento(idVistoria, forma, valor, data);
                
                if (sucesso) {
                    JOptionPane.showMessageDialog(dialog, "Pagamento realizado com sucesso!");
                    tableModel.setValueAt("Pago", selectedRow, 6); // Atualiza a tabela
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Erro ao realizar pagamento.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Valor inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelarBtn.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

 // --- Métodos de Lógica de Negócio ---

    /**
     * Realiza o pagamento de uma vistoria
     */
    private boolean realizarPagamento(int idVistoria, String formaPagamento, double valor, String data) {
        try {
            // 1. Primeiro, precisamos obter o idAgendamento da vistoria
            VistoriaDAO vistoriaDAO = new VistoriaDAO();
            Vistoria vistoria = vistoriaDAO.buscarVistoriaPorId(idVistoria);
            
            if (vistoria == null) {
                JOptionPane.showMessageDialog(this, "Vistoria não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            // 2. Inserir o pagamento no banco de dados
            PagamentoDAO pagamentoDAO = new PagamentoDAO();
            boolean pagamentoInserido = pagamentoDAO.inserirPagamento(
                vistoria.getIdAgendamento(), formaPagamento, valor, data
            );
            
            if (pagamentoInserido) {
                // 3. Atualizar o status de pagamento da vistoria
                boolean statusAtualizado = vistoriaDAO.atualizarStatusPagamento(idVistoria, "Pago");
                
                if (statusAtualizado) {
                    JOptionPane.showMessageDialog(this, "Pagamento realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Pagamento registrado, mas erro ao atualizar status.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao registrar pagamento.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao processar pagamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
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
        Cliente clienteExemplo = new Cliente(1, "João da Silva", "joao@email.com",
                "12345678900", "senha", "999999999");
        SwingUtilities.invokeLater(() -> new DashboardCliente(clienteExemplo));
    }
}