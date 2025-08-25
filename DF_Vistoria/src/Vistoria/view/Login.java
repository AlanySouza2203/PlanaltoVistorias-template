package Vistoria.view;

import Vistoria.dao.ClienteDAO;
import Vistoria.dao.FuncionarioDAO;
import Vistoria.model.Cliente;
import Vistoria.model.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

    public Login() {
        setTitle("SISTEMA DE VISTORIA VEICULOS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- Painel da Esquerda ---
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(187, 208, 235));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image image = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel("SISTEMA DE VISTORIA VEICULOS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel versionLabel = new JLabel("v0.5");
        versionLabel.setForeground(Color.BLACK);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 1)));
        leftPanel.add(titleLabel);
        leftPanel.add(versionLabel);
        leftPanel.add(Box.createVerticalGlue());

        // --- Painel da Direita (Formulário) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        JLabel welcomeLabel = new JLabel("Olá, faça seu login!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField cpfField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        JPanel cpfPanel = criarCampoComLabel("CPF/Matrícula:", cpfField);
        JPanel senhaPanel = criarCampoComLabel("Senha:", senhaField);

        JButton loginButton = new JButton("Login");
        estilizarBotao(loginButton);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel cadastroLabel = new JLabel("<html><u>Cadastre-se</u></html>");
        cadastroLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cadastroLabel.setForeground(new Color(33, 150, 243));
        cadastroLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastroLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Ação do botão de login ---
        loginButton.addActionListener(e -> {
            String matriculaOuCpf = cpfField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            if (matriculaOuCpf.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tenta fazer o login como FUNCIONÁRIO primeiro
            FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
            Funcionario funcionarioLogado = funcionarioDAO.login(matriculaOuCpf, senha);

            if (funcionarioLogado != null) {
                JOptionPane.showMessageDialog(this, "Login de Funcionário realizado com sucesso! Bem-vindo, " + funcionarioLogado.getNome() + ".");
                
                // --- MODIFICAÇÃO AQUI ---
                // Verifica o cargo do funcionário para direcionar ao dashboard correto
                if ("Vistoriador".equals(funcionarioLogado.getCargo())) {
                    new DashboardVistoriador(funcionarioLogado).setVisible(true);
                } else {
                    // Direciona para o dashboard do gerente (ou outro cargo)
                    new DashboardGerente(funcionarioLogado).setVisible(true);
                }
                dispose();
                return;
            }

            // Se não for um funcionário, tenta fazer o login como CLIENTE
            ClienteDAO clienteDAO = new ClienteDAO();
            Cliente clienteLogado = clienteDAO.login(matriculaOuCpf, senha);

            if (clienteLogado != null) {
                JOptionPane.showMessageDialog(this, "Login de Cliente realizado com sucesso! Bem-vindo, " + clienteLogado.getNome() + ".");
                new DashboardCliente(clienteLogado).setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "CPF ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Ação do JLabel de cadastro ---
        cadastroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CadastroCliente().setVisible(true);
            }
        });

        // --- Adicionando componentes ao painel direito ---
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(cpfPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(senhaPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(loginButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(cadastroLabel);

        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }

    private JPanel criarCampoComLabel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        inputField.setMaximumSize(new Dimension(300, 35));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCampo(inputField);

        panel.add(label);
        panel.add(inputField);

        return panel;
    }

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}