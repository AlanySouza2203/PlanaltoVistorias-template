package Vistoria.view;

import Vistoria.model.Agendamento;
import Vistoria.model.Cliente;
import Vistoria.model.Funcionario;
import Vistoria.dao.FuncionarioDAO;
import Vistoria.dao.PagamentoDAO;
import Vistoria.dao.PagamentoDAO.PagamentoInfo;
import Vistoria.dao.AgendamentoDAO;
import Vistoria.controller.FuncionarioController;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

public class DashboardGerente extends JFrame {

    private final Funcionario funcionarioLogado;
    private final FuncionarioController funcionarioController;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private DefaultTableModel modelFuncionarios;

    public DashboardGerente(Funcionario funcionario) {
        this.funcionarioLogado = funcionario;
        this.funcionarioController = new FuncionarioController();

        setTitle("Dashboard do Gerente - " + funcionarioLogado.getNome());
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(33, 150, 243));
        sidebarPanel.setPreferredSize(new Dimension(240, getHeight()));
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel titleLabel = new JLabel("Df Vistoria");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebarPanel.add(titleLabel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton btnCadastroFuncionario = criarBotaoLateral("Cadastro Funcionário");
        JButton btnListarFuncionario = criarBotaoLateral("Listar Funcionários");
        JButton btnRelatorios = criarBotaoLateral("Relatórios");
        JButton btnFinanceiro = criarBotaoLateral("Finanças");
        JButton btnLogout = criarBotaoLateral("Sair");

        sidebarPanel.add(btnCadastroFuncionario);
        sidebarPanel.add(btnListarFuncionario);
        sidebarPanel.add(btnRelatorios);
        sidebarPanel.add(btnFinanceiro);
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Adicionando as novas telas ao CardLayout
        cardPanel.add(criarPainelCadastroFuncionario(), "CadastroFuncionario");
        cardPanel.add(criarPainelListarFuncionario(), "ListarFuncionario");
        cardPanel.add(criarPainelRelatorios(), "Relatorios");
        cardPanel.add(criarPainelFinanceiro(), "Financas");

        btnCadastroFuncionario.addActionListener(e -> cardLayout.show(cardPanel, "CadastroFuncionario"));
        btnListarFuncionario.addActionListener(e -> {
            atualizarTabelaFuncionarios();
            cardLayout.show(cardPanel, "ListarFuncionario");
        });
        btnRelatorios.addActionListener(e -> cardLayout.show(cardPanel, "Relatorios"));
        btnFinanceiro.addActionListener(e -> cardLayout.show(cardPanel, "Financas"));

        btnLogout.addActionListener(e -> {
            dispose();
            new Login().setVisible(true);
        });

        add(sidebarPanel, BorderLayout.WEST);
        add(cardPanel, BorderLayout.CENTER);
        
        cardLayout.show(cardPanel, "CadastroFuncionario");

        setVisible(true);
    }

    private void atualizarTabelaFuncionarios() {
        modelFuncionarios.setRowCount(0);
        List<Funcionario> funcionarios = funcionarioController.listarFuncionarios();
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
            
            Funcionario novoFuncionario = new Funcionario(nome, email, matricula, senha, cargo);
            
            boolean sucesso = funcionarioController.cadastrarFuncionario(novoFuncionario);
            
            if (sucesso) {
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
            } else {
                JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            }
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

        String[] colunas = {"ID", "Nome", "Matrícula", "Cargo", "Email"};
        modelFuncionarios = new DefaultTableModel(colunas, 0);
        JTable table = new JTable(modelFuncionarios);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }

    // --- Nova tela para Relatórios e Agendamentos ---
    private JPanel criarPainelRelatorios() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();

        // DAO
        AgendamentoDAO agendamentoDAO = new AgendamentoDAO();
        FuncionarioDAO funcionariodao = new FuncionarioDAO();

        // Título
        JLabel title = new JLabel("Gerar Relatórios", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(title, gbc);

        // Label Status
        JLabel lblStatus = new JLabel("Status:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        panel.add(lblStatus, gbc);

        // Campo Status
        JTextField txtStatus = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        panel.add(txtStatus, gbc);

        // Tabela
        String[] colunas = {"ID", "Data", "Hora", "Status", "Cliente", "Veículo"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(modeloTabela);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        // Botão Buscar
        JButton btnBuscar = new JButton("Buscar");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnBuscar, gbc);

        // Método para popular tabela
        Runnable carregarTabela = () -> {
            modeloTabela.setRowCount(0); // limpa tabela
            List<Agendamento> agendamentos = agendamentoDAO.listarAgendamentos(); // novo método no DAO
            for (Agendamento ag : agendamentos) {
                modeloTabela.addRow(new Object[]{
                	    ag.getIdAgendamento(),
                	    ag.getData_agendamento(),
                	    ag.getHora(),
                	    ag.getStatus_agendamento(),
                	    ag.getCliente().getNome(),
                	  ag.getVeiculo().getNome_veiculo()
                });
            }
        };

        // Ação do botão Buscar
        btnBuscar.addActionListener(e -> {
            String status = txtStatus.getText().trim();

            modeloTabela.setRowCount(0); // limpa tabela

            List<Agendamento> agendamentos;
            if (status.isEmpty()) {
                agendamentos = agendamentoDAO.listarAgendamentos();
            } else {
                agendamentos = funcionariodao.listarAgendamentosPorStatus(status);
            }

            for (Agendamento ag : agendamentos) {
                modeloTabela.addRow(new Object[]{
                    ag.getIdAgendamento(),
                    ag.getData_agendamento(),
                    ag.getHora(),
                    ag.getStatus_agendamento(),
                    ag.getCliente().getNome(),
                    ag.getVeiculo().getNome_veiculo()
                });
            }

            if (agendamentos.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Nenhum agendamento encontrado.");
            }
        });

        // Carrega todos ao iniciar
        carregarTabela.run();

        return panel;
    }
    
    // --- Nova tela para Finanças ---
    private JPanel criarPainelFinanceiro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 240, 240));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel title = new JLabel("Controle Financeiro", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        panel.add(title, gbc);

        // Tabela de pagamentos
        gbc.gridy++;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        String[] colunas = {"ID Pagamento", "Nome do Cliente", "Valor Pago", "Forma de Pagamento", "Data do Pagamento"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        JTable tabela = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setPreferredSize(new Dimension(700, 300));
        panel.add(scrollPane, gbc);

        // Carregar dados da DAO
        PagamentoDAO dao = new PagamentoDAO();
        List<PagamentoInfo> pagamentos = dao.listarPagamentosPagos();

        for (PagamentoInfo info : pagamentos) {
            model.addRow(new Object[]{
                info.idPagamento,
                info.nomeCliente,
                info.valor,
                info.formaPagamento,
                info.dataPagamento
            });
        }

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
		// TODO Auto-generated method stub

	}
}