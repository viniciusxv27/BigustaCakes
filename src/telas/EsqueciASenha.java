package telas;

import conexao.ConexaoDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EsqueciASenha extends javax.swing.JFrame {

    private JTextField emailTextField;

    public EsqueciASenha() {
        setTitle("Esqueci a senha");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 1, 10, 10)); // Layout vertical com espaçamento

        JLabel emailLabel = new JLabel("Digite seu e-mail:");
        panel.add(emailLabel);

        emailTextField = new JTextField();
        panel.add(emailTextField);

        JButton enviarEmailButton = new JButton("Enviar E-mail");
        enviarEmailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarEmail();
            }
        });
        panel.add(enviarEmailButton);

        pack();
    }

    private void enviarEmail() {
        String email = emailTextField.getText().trim();

        // Verificar se o e-mail existe no banco de dados
        if (verificarEmailNoBanco(email)) {
            JOptionPane.showMessageDialog(this, "Um e-mail foi enviado para " + email + " com instruções para redefinir sua senha.", "E-mail Enviado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "E-mail não encontrado! Verifique o e-mail digitado.", "E-mail não encontrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean verificarEmailNoBanco(String email) {
        ConexaoDAO conexaoDAO = new ConexaoDAO();
        Connection conexao = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean encontrado = false;

        try {
            conexao = conexaoDAO.conectaBD();
            String sql = "SELECT * FROM usuarios WHERE email = ?";
            pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();

            encontrado = rs.next(); // Se encontrou algum registro, retorna true

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao verificar e-mail: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conexao != null) conexao.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return encontrado;
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EsqueciASenha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new EsqueciASenha().setVisible(true);
        });
    }
}
