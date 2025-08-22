package Vistoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

    public Login() {
        setTitle("SISTEMA DE VISTORIA VEICULOS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- Painel da esquerda com fundo e logo ---
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(187, 208, 235));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        // Carrega e redimensiona a imagem
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

        // --- Painel da direita (formulário de login) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));
        // A linha abaixo foi removida para usar a cor padrão do sistema
        // rightPanel.setBackground(Color.WHITE); 

        JLabel welcomeLabel = new JLabel("Olá, faça seu login!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Cria e adiciona os campos com rótulos ---
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        
        JPanel userPanel = criarCampoComLabel("Usuário", userField);
        JPanel passPanel = criarCampoComLabel("Senha", passField);
        
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Ação do botão de login ---
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = userField.getText();
                String senha = new String(passField.getPassword());

                if (usuario.equals("admin") && senha.equals("1234")) {
                    JOptionPane.showMessageDialog(null, "Login realizado com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- Adiciona os componentes ao painel da direita ---
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(userPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(passPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(loginButton);

        add(leftPanel);
        add(rightPanel);

        setVisible(true);
    }

    /**
     * Cria um JPanel que agrupa um JLabel e um JTextField/JPasswordField,
     * garantindo o alinhamento correto.
     */
    private JPanel criarCampoComLabel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza este painel

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        inputField.setMaximumSize(new Dimension(300, 35));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCampo((JComponent) inputField);
        
        panel.add(label);
        panel.add(inputField);

        return panel;
    }
    
    // Estilo padrão dos campos
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
                estilizarCampo(campo); // Retorna ao estilo padrão
            }
        });
    }

    // Método principal para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}