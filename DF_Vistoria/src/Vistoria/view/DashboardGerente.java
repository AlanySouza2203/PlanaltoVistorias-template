package Vistoria.view;

import Vistoria.dao.FuncionarioDAO;
import Vistoria.dao.ClienteDAO;
import Vistoria.model.Cliente;
import Vistoria.model.Funcionario;
import Vistoria.model.Agendamento; // Se você precisar exibir agendamentos de funcionários

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class DashboardGerente extends JFrame {

    // Ajustamos para receber um objeto Funcionario
    private final Funcionario funcionarioLogado;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Adicionamos os DAOs
    private final FuncionarioDAO funcionarioDAO;
    private final ClienteDAO clienteDAO;

    public DashboardGerente(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;
        this.funcionarioDAO = new FuncionarioDAO();
        this.clienteDAO = new ClienteDAO();

        // Título ajustado para o nome do gerente
        setTitle("Dashboard do Gerente - " + funcionarioLogado.getNome());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Painel Lateral (Barra de Navegação) ---
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(33, 150, 243));
        sidebarPanel.setPreferredSize(new Dimension(260, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel titleLabel = new JLabel("Df Vistoria");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(titleLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botões de navegação
        JButton btnCadastroFuncionario = criarBotaoLateral("Cadastro Funcionário");
        JButton btnListarFuncionario = criarBotaoLateral("Listar Funcionários");
        JButton btnListarCliente = criarBotaoLateral("Listar Clientes");
        JButton btnListarAgendamentos = criarBotaoLateral("Listar Agendamentos");
        JButton btnLogout = criarBotaoLateral("Sair");

        sidebarPanel.add(btnCadastroFuncionario);
        sidebarPanel.add(btnListarFuncionario);
        sidebarPanel.add(btnListarCliente);
        sidebarPanel.add(btnListarAgendamentos);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);

        // --- Painel de Conteúdo Principal (CardLayout) ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Adiciona os "cartões" (telas) ao painel de conteúdo
        cardPanel.add(criarPainelCadastroFuncionario(), "CadastroFuncionario");
        cardPanel.add(criarPainelListarFuncionario(), "ListarFuncionario");
        cardPanel.add(criarPainelListarCliente(), "ListarCliente");
        cardPanel.add(criarPainelListarAgendamentos(), "ListarAgendamentos");

        // Ações dos botões da barra lateral
        btnCadastroFuncionario.addActionListener(e -> cardLayout.show(cardPanel, "CadastroFuncionario"));
        btnListarFuncionario.addActionListener(e -> {
            atualizarTabelaFuncionarios();
            cardLayout.show(cardPanel, "ListarFuncionario");
        });
        btnListarCliente.addActionListener(e -> {
            atualizarTabelaClientes();
            cardLayout.show(cardPanel, "ListarCliente");
        });
        btnListarAgendamentos.addActionListener(e -> {
            // Lógica para atualizar a tabela de agendamentos
            cardLayout.show(cardPanel, "ListarAgendamentos");
        });
        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        
        // Exibe o painel de cadastro de funcionário por padrão
        cardLayout.show(cardPanel, "CadastroFuncionario");

        setVisible(true);
    }

    // --- Métodos de atualização de tabelas ---
    
    // Tabela para listar Funcionários
    private DefaultTableModel modelFuncionarios;
    private void atualizarTabelaFuncionarios() {
        modelFuncionarios.setRowCount(0); // Limpa a tabela
        List<Funcionario> funcionarios = funcionarioDAO.listarTodos(); 
        for (Funcionario f : funcionarios) {
            modelFuncionarios.addRow(new Object[]{
                f.getIdFuncionario(),
                f.getNome(),
                f.getMatricula(),
                f.getCargo(),
                f.getEmail()
            });
        }
    }
    
    // Tabela para listar Clientes
    private DefaultTableModel modelClientes;
    private void atualizarTabelaClientes() {
        modelClientes.setRowCount(0); // Limpa a tabela
        List<Cliente> clientes = clienteDAO.listar(); 
        for (Cliente c : clientes) {
            modelClientes.addRow(new Object[]{
                c.getIdCliente(),
                c.getNome(),
                c.getCpf(),
                c.getEmail()
            });
        }
    }
    
    // --- Métodos de criação de painéis ---

    private JPanel criarPainelCadastroFuncionario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Cadastrar Novo Funcionário", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        JTextField nomeField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField matriculaField = new JTextField(20);
        JPasswordField senhaField = new JPasswordField(20);
        JTextField cargoField = new JTextField("Vistoriador", 20);
        cargoField.setEditable(false);
        cargoField.setBackground(new Color(220, 220, 220));

        JButton cadastrarButton = new JButton("Cadastrar");
        estilizarBotao(cadastrarButton);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panel.add(criarCampoComLabel("Nome", nomeField), gbc);
        gbc.gridx = 1; panel.add(criarCampoComLabel("Email", emailField), gbc);

        gbc.gridy = 2; gbc.gridx = 0; panel.add(criarCampoComLabel("Matrícula", matriculaField), gbc);
        gbc.gridx = 1; panel.add(criarCampoComLabel("Senha", senhaField), gbc);

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(criarCampoComLabel("Cargo", cargoField), gbc);
        
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2; panel.add(cadastrarButton, gbc);

        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String email = emailField.getText().trim();
            String matricula = matriculaField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();
            String cargo = cargoField.getText();

            if (nome.isEmpty() || email.isEmpty() || matricula.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Funcionario novoFuncionario = new Funcionario(nome, email, matricula, senha, cargo);
            funcionarioDAO.cadastrarFuncionario(novoFuncionario);

            String mensagemSucesso = "<html><b>Funcionário Cadastrado com Sucesso!</b><br><br>"
                + "<b>Cargo:</b> " + cargo + "<br>"
                + "<b>Nome:</b> " + nome + "<br>"
                + "<b>Email:</b> " + email + "<br>"
                + "<b>Matrícula:</b> " + matricula + "<br>"
                + "</html>";

            JOptionPane.showMessageDialog(this, mensagemSucesso, "Cadastro Concluído", JOptionPane.INFORMATION_MESSAGE);

            nomeField.setText("");
            emailField.setText("");
            matriculaField.setText("");
            senhaField.setText("");
        });

        return panel;
    }
    
    private JPanel criarPainelListarFuncionario() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Listar Funcionários", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        // Tabela para exibir os funcionários
        String[] colunas = {"ID", "Nome", "Matrícula", "Cargo", "Email"};
        modelFuncionarios = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(modelFuncionarios);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    private JPanel criarPainelListarCliente() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Listar Clientes", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        // Tabela para exibir os clientes
        String[] colunas = {"ID", "Nome", "CPF", "Email"};
        modelClientes = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(modelClientes);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
    
    private JPanel criarPainelListarAgendamentos() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(240, 240, 240));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Listar Agendamentos", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, BorderLayout.NORTH);

        // Implementação da tabela de agendamentos (similar às outras)
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

    // --- Métodos de estilo ---
    private JButton criarBotaoLateral(String texto) {
        JButton button = new JButton(texto);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
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

    private void estilizarCampo(JComponent campo) {
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            new EmptyBorder(5, 10, 5, 10)
        ));
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(33, 150, 243), 2, true),
                    new EmptyBorder(5, 10, 5, 10)
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
        // Exemplo de como inicializar a tela com um funcionário
        Funcionario gerenteExemplo = new Funcionario(1, "Ana Paula", "ana@email.com", "12345", "admin", "Gerente");
        SwingUtilities.invokeLater(() -> new DashboardGerente(gerenteExemplo));
    }
}