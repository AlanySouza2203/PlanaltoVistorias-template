package Vistoria.view;

import Vistoria.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DashboardCliente extends JFrame {

    private final Cliente clienteLogado;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public DashboardCliente(Cliente cliente) {
        this.clienteLogado = cliente;

        setTitle("Dashboard do Cliente - " + cliente.getNome());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Painel Lateral (Barra de Navegação) ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(33, 150, 243));
        sidebarPanel.setPreferredSize(new Dimension(220, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JLabel titleLabel = new JLabel("Vistoria Fácil");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(titleLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botões de navegação
        JButton btnDashboard = criarBotaoLateral("Dashboard");
        JButton btnAgendar = criarBotaoLateral("Agendar Vistoria");
        JButton btnCadastrar = criarBotaoLateral("Cadastrar Veículo");
        JButton btnLaudos = criarBotaoLateral("Emitir Laudo");
        JButton btnLogout = criarBotaoLateral("Sair");

        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(btnAgendar);
        sidebarPanel.add(btnCadastrar);
        sidebarPanel.add(btnLaudos);
        sidebarPanel.add(Box.createVerticalGlue()); // Empurra o botão de logout para baixo
        sidebarPanel.add(btnLogout);

        // --- Painel de Conteúdo Principal (CardLayout) ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Adiciona os "cartões" (telas) ao painel de conteúdo
        cardPanel.add(criarPainelDashboardInicial(), "Dashboard");
        cardPanel.add(criarPainelAgendarVistoria(), "AgendarVistoria");
        cardPanel.add(criarPainelCadastrarVeiculo(), "CadastrarVeiculo");
        cardPanel.add(criarPainelEmitirLaudo(), "EmitirLaudo");

        // Ações dos botões da barra lateral
        btnDashboard.addActionListener(e -> cardLayout.show(cardPanel, "Dashboard"));
        btnAgendar.addActionListener(e -> cardLayout.show(cardPanel, "AgendarVistoria"));
        btnCadastrar.addActionListener(e -> cardLayout.show(cardPanel, "CadastrarVeiculo"));
        btnLaudos.addActionListener(e -> cardLayout.show(cardPanel, "EmitirLaudo"));
        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private JPanel criarPainelDashboardInicial() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel welcomeLabel = new JLabel("Bem-vindo, " + clienteLogado.getNome() + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        panel.add(welcomeLabel, gbc);

        // Cards de informação (inspirado na imagem)
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;

        JPanel card1 = criarCardInfo("Vistorias Agendadas", "0");
        gbc.gridx = 0; panel.add(card1, gbc);

        JPanel card2 = criarCardInfo("Laudos Concluídos", "0");
        gbc.gridx = 1; panel.add(card2, gbc);

        JPanel card3 = criarCardInfo("Veículos Cadastrados", "0");
        gbc.gridx = 2; panel.add(card3, gbc);

        return panel;
    }

    private JPanel criarCardInfo(String titulo, String valor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel titleLabel = new JLabel(titulo);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        card.add(titleLabel, BorderLayout.NORTH);

        JLabel valueLabel = new JLabel(valor, SwingConstants.CENTER);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JButton criarBotaoLateral(String texto) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMaximumSize(new Dimension(200, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 180, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 150, 243));
            }
        });
        return button;
    }

    private JPanel criarPainelAgendarVistoria() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Agendar Nova Vistoria");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        JTextField dataField = new JTextField(20);
        JTextField horaField = new JTextField(20);
        JComboBox<String> tipoVistoria = new JComboBox<>(new String[]{"Transferência", "Cautelar", "Estrutural"});
        JButton agendarButton = new JButton("Agendar");
        estilizarBotao(agendarButton);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(criarCampoComLabel("Data da Vistoria", dataField), gbc);
        gbc.gridx = 1; panel.add(criarCampoComLabel("Hora da Vistoria", horaField), gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(criarCampoComLabel("Tipo de Vistoria", tipoVistoria), gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(agendarButton, gbc);

        agendarButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Vistoria agendada para: " + dataField.getText());
        });

        return panel;
    }

    private JPanel criarPainelCadastrarVeiculo() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Cadastrar Novo Veículo");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        JTextField placaField = new JTextField(20);
        JTextField marcaField = new JTextField(20);
        JTextField modeloField = new JTextField(20);
        JButton cadastrarButton = new JButton("Cadastrar");
        estilizarBotao(cadastrarButton);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(criarCampoComLabel("Placa", placaField), gbc);
        gbc.gridx = 1; panel.add(criarCampoComLabel("Marca", marcaField), gbc);

        gbc.gridy = 2; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(criarCampoComLabel("Modelo", modeloField), gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(cadastrarButton, gbc);

        cadastrarButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Veículo com placa " + placaField.getText() + " cadastrado com sucesso!");
        });

        return panel;
    }

    private JPanel criarPainelEmitirLaudo() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Laudos de Vistoria", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Laudo de Vistoria 01 - 15/08/2025");
        listModel.addElement("Laudo de Vistoria 02 - 10/07/2025");
        listModel.addElement("Laudo de Vistoria 03 - 20/06/2025");
        JList<String> laudosList = new JList<>(listModel);
        laudosList.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(laudosList);

        JButton emitirButton = new JButton("Emitir Laudo");
        estilizarBotao(emitirButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(emitirButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        emitirButton.addActionListener(e -> {
            String selected = laudosList.getSelectedValue();
            if (selected != null) {
                JOptionPane.showMessageDialog(this, "Emitindo laudo para: " + selected);
            } else {
                JOptionPane.showMessageDialog(this, "Selecione um laudo para emitir.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    private JPanel criarCampoComLabel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setMaximumSize(new Dimension(300, 35));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCampo(inputField);

        panel.add(label);
        panel.add(Box.createVerticalStrut(2));
        panel.add(inputField);
        return panel;
    }
    
    // Métodos de estilo
    private void estilizarCampo(JComponent campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                estilizarCampo(campo);
            }
        });
    }

    private void estilizarBotao(JButton button) {
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(200, 40));
    }

    public static void main(String[] args) {
        Cliente clienteExemplo = new Cliente(1, "João da Silva", "joao@email.com", "12345678900", "senha", "999999999");
        SwingUtilities.invokeLater(() -> new DashboardCliente(clienteExemplo));
    }
}