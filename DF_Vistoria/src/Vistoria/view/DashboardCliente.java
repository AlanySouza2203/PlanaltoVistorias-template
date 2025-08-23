package Vistoria.view;

import Vistoria.model.Cliente;
import Vistoria.model.Agendamento;
import Vistoria.model.Veiculo;
import Vistoria.controller.AgendamentoController;
import Vistoria.controller.VeiculoController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class DashboardCliente extends JFrame {

    private final Cliente clienteLogado;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Controladores
    private final AgendamentoController agendamentoController;
    private final VeiculoController veiculoController;

    // Componentes de formulário
    private JComboBox<String> veiculoAgendarComboBox;
    
    // Mapeamento de Placa para Veículo para uso no JComboBox
    private Map<String, Veiculo> veiculoMap = new HashMap<>();

    // Paleta de cores
    private static final Color SIDEBAR_COLOR = new Color(33, 150, 243); // Azul lateral
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245); // Fundo cinza claro
    private static final Color CARD_BACKGROUND = Color.WHITE;

    // Cores dos títulos dos cards
    private static final Color CARD_TITLE_ORANGE = new Color(255, 152, 0);
    private static final Color CARD_TITLE_GREEN = new Color(0, 150, 136);
    private static final Color CARD_TITLE_RED = new Color(244, 67, 54);

    // Cor dos números nos cards
    private static final Color CARD_VALUE_BLUE = new Color(33, 150, 243);

    public DashboardCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        this.agendamentoController = new AgendamentoController();
        this.veiculoController = new VeiculoController();

        setTitle("Dashboard do Cliente");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Sidebar ---
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

        // Botões da barra lateral
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

        // --- Painel principal ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(BACKGROUND_COLOR);
        cardPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        cardPanel.add(criarPainelDashboardInicial(), "Dashboard");
        cardPanel.add(criarPainelAgendarVistoria(), "AgendarVistoria");
        cardPanel.add(criarPainelCadastrarVeiculo(), "CadastrarVeiculo");
        cardPanel.add(criarPainelEmitirLaudo(), "EmitirLaudo");

        // Ações dos botões
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

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
        atualizarCardsDashboard(); // Carrega os dados iniciais
    }
    
    // --- Métodos de Lógica de Negócio ---

    /**
     * Carrega os veículos do banco de dados e popula o JComboBox.
     */
    private void carregarVeiculosComboBox() {
        // Limpa o JComboBox e o mapa antes de carregar novos dados
        veiculoAgendarComboBox.removeAllItems();
        veiculoMap.clear();

        List<Veiculo> veiculos = veiculoController.listarVeiculos();
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
     * Atualiza os cards do dashboard com dados do banco de dados (atualmente mock).
     */
    private void atualizarCardsDashboard() {
        // TODO: Implementar a lógica para buscar a contagem real de vistorias, laudos e veículos do BD
        // Exemplo: int numAgendamentos = agendamentoController.contarAgendamentosDoCliente(clienteLogado.getIdCliente());
        // cardAgendamentos.setText(String.valueOf(numAgendamentos));
    }

    // --- Método para carregar e redimensionar ícones ---
    private ImageIcon carregarIcone(String path, int largura, int altura) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image image = icon.getImage().getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    // --- Painel inicial com cards ---
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

        cardsPanel.add(criarCardInfo("Vistorias Agendadas", "0", CARD_TITLE_ORANGE, "/icones/task.png"));
        cardsPanel.add(criarCardInfo("Laudos Concluídos", "0", CARD_TITLE_GREEN, "/icones/check.png"));
        cardsPanel.add(criarCardInfo("Veículos Cadastrados", "0", CARD_TITLE_RED, "/icones/carro.png"));

        panel.add(cardsPanel);
        return panel;
    }

    // --- Cards com ícones (32x32) ---
    private JPanel criarCardInfo(String titulo, String valor, Color titleColor, String iconPath) {
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
        titleLabel.setForeground(titleColor);

        JLabel valueLabel = new JLabel(valor);
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

    // --- Botões da sidebar ---
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

    // --- Painel Agendar Vistoria ---
    private JPanel criarPainelAgendarVistoria() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Agendar Nova Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(title, gbc);

        JTextField dataField = new JTextField(20);
        JTextField horaField = new JTextField(20);
        JComboBox<String> tipoVistoria = new JComboBox<>(new String[]{"Transferência", "Cautelar", "Estrutural"});
        
        veiculoAgendarComboBox = new JComboBox<>(); // JComboBox agora é um atributo da classe
        
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

    // --- Formulário Cadastrar Veículo ---
    private JPanel criarPainelCadastrarVeiculo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Cadastrar Veículo");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST; panel.add(title, gbc);

        // Campos do formulário
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

    // --- Painel Emitir Laudo ---
    private JPanel criarPainelEmitirLaudo() {
        JPanel panel = new JPanel(new BorderLayout(20, 20));
        panel.setBackground(BACKGROUND_COLOR);

        JLabel title = new JLabel("Laudos de Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.DARK_GRAY);
        panel.add(title, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Laudo 01 - 15/08/2025");
        listModel.addElement("Laudo 02 - 10/07/2025");
        listModel.addElement("Laudo 03 - 20/06/2025");

        JList<String> laudosList = new JList<>(listModel);
        laudosList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(laudosList);

        JButton emitirButton = new JButton("Emitir Laudo");
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(emitirButton);

        emitirButton.addActionListener(e -> {
            String selected = laudosList.getSelectedValue();
            if (selected != null) {
                JOptionPane.showMessageDialog(this, "Emitindo " + selected);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um laudo.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        Cliente clienteExemplo = new Cliente(1, "João da Silva", "joao@email.com",
                "12345678900", "senha", "999999999");
        SwingUtilities.invokeLater(() -> new DashboardCliente(clienteExemplo));
    }
}