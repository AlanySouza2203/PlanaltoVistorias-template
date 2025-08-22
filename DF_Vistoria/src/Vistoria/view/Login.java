package Vistoria.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login extends JFrame {

    /**
     * Construtor da classe Login.
     * Configura a janela principal da aplicação, criando e posicionando
     * todos os componentes da interface do usuário.
     */
    public Login() {
        // --- Configurações básicas da janela ---
        setTitle("SISTEMA DE VISTORIA VEICULOS - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Encerra a aplicação ao fechar a janela
        setSize(800, 600); // Define o tamanho da janela
        setLocationRelativeTo(null); // Centraliza a janela na tela
        setLayout(new GridLayout(1, 2)); // Divide a janela em 2 colunas para os painéis

        // --- Configuração do Painel da Esquerda ---
        // Este painel exibe a logo e informações do sistema.
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(187, 208, 235)); // Define a cor de fundo azul clara
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Layout vertical para os componentes
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30)); // Adiciona preenchimento interno

        // --- Carrega e exibe a imagem (logo) ---
        // Pega a imagem do diretório de recursos e a redimensiona.
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/logo.png"));
        Image image = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza horizontalmente a imagem

        // --- Rótulos de Título e Versão ---
        // Exibem o nome do sistema e a versão.
        JLabel titleLabel = new JLabel("SISTEMA DE VISTORIA VEICULOS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel versionLabel = new JLabel("v0.5");
        versionLabel.setForeground(Color.BLACK);
        versionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        versionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Adiciona os componentes ao Painel da Esquerda ---
        // Usa "glues" para empurrar os componentes para o centro do painel.
        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(iconLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 1)));
        leftPanel.add(titleLabel);
        leftPanel.add(versionLabel);
        leftPanel.add(Box.createVerticalGlue());

        // --- Configuração do Painel da Direita (Formulário de Login) ---
        // Este painel contém os campos de entrada e o botão.
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(60, 50, 60, 50));
        // A cor de fundo é a padrão do sistema (não definida aqui).

        JLabel welcomeLabel = new JLabel("Olá, faça seu login!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o texto de boas-vindas

        // --- Criação dos campos de entrada e seus rótulos ---
        JTextField userField = new JTextField();
        JPasswordField passField = new JPasswordField();
        
        // Agrupa o rótulo e o campo em um painel para alinhamento correto.
        JPanel userPanel = criarCampoComLabel("Usuário", userField);
        JPanel passPanel = criarCampoComLabel("Senha", passField);
        
        // --- Botão de Login ---
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(33, 150, 243));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false); // Remove a borda de foco
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- Ação do Botão de Login ---
        // Define o que acontece quando o botão é clicado.
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

        // --- Adiciona todos os componentes ao Painel da Direita ---
        rightPanel.add(welcomeLabel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Espaçamento vertical
        rightPanel.add(userPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        rightPanel.add(passPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(loginButton);

        // Adiciona os painéis principal da esquerda e direita à janela
        add(leftPanel);
        add(rightPanel);

        setVisible(true); // Torna a janela visível
    }

    /**
     * Método auxiliar para criar um JPanel que agrupa um JLabel e um componente de entrada de texto.
     * Isso garante que o rótulo e o campo fiquem alinhados corretamente.
     * @param labelText O texto do rótulo (ex: "Usuário").
     * @param inputField O campo de entrada (JTextField ou JPasswordField).
     * @return Um JPanel contendo o rótulo e o campo, alinhados.
     */
    private JPanel criarCampoComLabel(String labelText, JComponent inputField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centraliza o painel agrupador

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinha o rótulo à esquerda
        
        // Configura o tamanho e a fonte do campo de entrada
        inputField.setMaximumSize(new Dimension(300, 35));
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        inputField.setAlignmentX(Component.LEFT_ALIGNMENT);
        estilizarCampo(inputField); // Aplica os estilos de borda e foco

        panel.add(label);
        panel.add(inputField);

        return panel;
    }
    
    /**
     * Método auxiliar para aplicar estilos de borda e um FocusListener a um componente.
     * Muda a borda para azul quando o componente ganha o foco.
     * @param campo O componente a ser estilizado (JTextField ou JPasswordField).
     */
    private void estilizarCampo(JComponent campo) {
        // Estilo de borda padrão (cinza)
        campo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        // Listener para mudar o estilo da borda quando o foco é ganho ou perdido
        campo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Borda azul quando o campo está em foco
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

    /**
     * Método principal (main).
     * O ponto de entrada da aplicação. Garante que a interface seja
     * criada na thread de despacho de eventos da AWT, que é segura para Swing.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Login::new);
    }
}