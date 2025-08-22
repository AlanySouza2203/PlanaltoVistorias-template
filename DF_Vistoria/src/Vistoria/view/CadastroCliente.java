package Vistoria.view;

import Vistoria.dao.ClienteDAO;
import Vistoria.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CadastroCliente extends JFrame {

    public CadastroCliente() {
        setTitle("SISTEMA DE VISTORIA VEICULOS - Cadastro");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // --- Painel da Esquerda (Logo e Nome do Sistema) ---
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

        JLabel welcomeLabel = new JLabel("Cadastro de Cliente");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField nomeField = new JTextField();
        JTextField cpfField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField telefoneField = new JTextField();
        JPasswordField senhaField = new JPasswordField();

        JPanel nomePanel = criarCampoComLabel("Nome:", nomeField);
        JPanel cpfPanel = criarCampoComLabel("CPF:", cpfField);
        JPanel telefonePanel = criarCampoComLabel("Telefone:", telefoneField);
        JPanel emailPanel = criarCampoComLabel("Email:", emailField);
        JPanel senhaPanel = criarCampoComLabel("Senha:", senhaField);

        JButton cadastrarButton = new JButton("Cadastrar");
        estilizarBotao(cadastrarButton);
        cadastrarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- AÇÃO DO BOTÃO ---
        cadastrarButton.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String telefone = telefoneField.getText().trim();
            String email = emailField.getText().trim();
            String senha = new String(senhaField.getPassword()).trim();

            if (nome.isEmpty() || cpf.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cliente cliente = new Cliente(0, nome, cpf, telefone, email, senha);
            ClienteDAO dao = new ClienteDAO();

            if (dao.inserir(cliente)) {
                JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
                nomeField.setText("");
                cpfField.setText("");
                telefoneField.setText("");
                emailField.setText("");
                senhaField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar cliente!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Adicionando ao painel direito ---
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(nomePanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(cpfPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(telefonePanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(emailPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(senhaPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(cadastrarButton);

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
        SwingUtilities.invokeLater(CadastroCliente::new);
    }
}
