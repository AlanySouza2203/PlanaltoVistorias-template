package Vistoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

    //private final LoginController loginController;

    public Login() {
        //this.loginController = new LoginController();

        setTitle("SISTEMA DE VISTORIA VEICULOS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- Painel da Esquerda (mesmo código que você já tinha) ---
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

        // --- Painel da Direita (Formulário de Login) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(new Color(255, 255, 255));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));

        JLabel welcomeLabel = new JLabel("Olá, faça seu login!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();

        JPanel userPanel = criarCampoComLabel("CPF: ", userField);
        JPanel passPanel = criarCampoComLabel("Senha: ", passField);
        userPanel.setBackground(new Color(255, 255, 255));
        passPanel.setBackground(new Color(255, 255, 255));
        
        JButton loginButton = new JButton("Login");
        estilizarBotao(loginButton);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Alteração: Trocar o botão por um JLabel sublinhado ---
        JLabel cadastroLabel = new JLabel("<html><u>Cadastre-se</u></html>");
        cadastroLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cadastroLabel.setForeground(new Color(33, 150, 243)); // Cor de link
        cadastroLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cadastroLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        /*
        // --- Ação para o JLabel de cadastro ---
        cadastroLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new CadastroCliente().setVisible(true);
            }
        });

        // --- Ação do Botão de Login (mantida a mesma lógica) ---
        loginButton.addActionListener(e -> {
            String matricula = userField.getText();
            String senha = new String(passField.getPassword());
            Funcionario funcionarioLogado = loginController.realizarLogin(matricula, senha);

            if (funcionarioLogado != null) {
                JOptionPane.showMessageDialog(Login.this, "Login realizado com sucesso! Bem-vindo, " + funcionarioLogado.getNome() + ".");
                // Lógica para abrir a próxima tela (Dashboard)
            } else {
                JOptionPane.showMessageDialog(Login.this, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
		*/
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(userPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(passPanel);
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